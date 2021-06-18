package com.app.exceedjobs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.exceedjobs.R;
import com.app.exceedjobs.adapter.MessageAdapter;
import com.app.exceedjobs.api.UserApi;
import com.app.exceedjobs.model.JobModel;
import com.app.exceedjobs.model.MessageModel;
import com.app.exceedjobs.utility.InternetCheck;
import com.app.exceedjobs.utility.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    //const

    //var
    private String paymentStatus = "0";
    private UserApi mUserApi;
    private List<MessageModel> messages;

    //widgets
    private MaterialToolbar toolbar;
    private RecyclerView messages_RV;
    private TextView noMessage_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getPaymentStatus();

        messages_RV = findViewById(R.id.message_RV);
        noMessage_TV = findViewById(R.id.noMessages_TV);

        noMessage_TV.setVisibility(View.GONE);

        mUserApi = RetrofitClient.getClient().create(UserApi.class);
        messages = new ArrayList<>();

        loadContents();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void loadContents() {

        new InternetCheck(internet -> {
            if (internet){
                getMessages();

            }else {
                Log.d("INTERNET","NO INTERNET");
                final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Bad network connection. Try again", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", view -> {
                    loadContents();
                    snackbar.dismiss();
                });
                snackbar.show();
            }
        });

    }

    private void getMessages() {

        Call<List<MessageModel>> call = mUserApi.getMessages();
        call.enqueue(new Callback<List<MessageModel>>() {
            @Override
            public void onResponse(Call<List<MessageModel>> call, Response<List<MessageModel>> response) {
                if (!response.isSuccessful()){
                    final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Error fetching messages. Try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Retry", view -> {
                        loadContents();
                        snackbar.dismiss();
                    });
                    snackbar.show();

                }else {

                    List<MessageModel> responseMessages = response.body();

                    for (MessageModel message : responseMessages){
                        if (message.getUserType().equals(paymentStatus)) messages.add(message);
                    }

                    if (messages.size()==0) noMessage_TV.setVisibility(View.VISIBLE);
                    else {
                        initialiseMessagesRV(messages);

                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).edit();
                        editor.putInt("messageCount",messages.size());
                        editor.apply();
                    }



                }
            }

            @Override
            public void onFailure(Call<List<MessageModel>> call, Throwable t) {
                final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Please try again", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        });

    }

    private void initialiseMessagesRV(List<MessageModel> messages) {

        Log.d("Messages", String.valueOf(messages.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        messages_RV.setLayoutManager(linearLayoutManager);
        MessageAdapter adapter = new MessageAdapter(messages,this);
        messages_RV.setAdapter(adapter);

    }

    private void getPaymentStatus() {
        paymentStatus = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).getString("paymentStatus","0");
        Log.d("STATUS",paymentStatus);
    }
}