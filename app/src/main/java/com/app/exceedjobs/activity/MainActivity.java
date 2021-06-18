package com.app.exceedjobs.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.exceedjobs.R;
import com.app.exceedjobs.adapter.JobsAdapter;
import com.app.exceedjobs.api.JobApi;
import com.app.exceedjobs.api.UserApi;
import com.app.exceedjobs.model.JobModel;
import com.app.exceedjobs.model.MessageModel;
import com.app.exceedjobs.model.UserModel;
import com.app.exceedjobs.utility.InternetCheck;
import com.app.exceedjobs.utility.RetrofitClient;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class MainActivity extends AppCompatActivity {

    //const

    //var
    private FirebaseAuth mAuth;
    private JobApi mJobApi;
    private UserApi mUserApi;
    private String userPhone, paymentStatus="0";
    private int messageCount=0;

    //widgets
    private RecyclerView jobs_RV;
    private ProgressIndicator progressIndicator;
    private ImageBadgeView message_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        jobs_RV = findViewById(R.id.jobs_RV);
        progressIndicator = findViewById(R.id.progressBar);
        message_IV = findViewById(R.id.message_IV);

        mJobApi = RetrofitClient.getClient().create(JobApi.class);
        mUserApi = RetrofitClient.getClient().create(UserApi.class);

        if (!isLogin())  startActivity(new Intent(this,CoverActivity.class));
        else loadContents();

//        loadContents();
    }


    private void loadContents() {
        progressIndicator.setVisibility(View.VISIBLE);

        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {
                if (internet){

                    getJobs();
                    getUser();
                    getMessages();

                }else {
                    progressIndicator.setVisibility(View.GONE);
                    Log.d("INTERNET","NO INTERNET");
                    final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Bad network connection. Try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadContents();
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
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
                        if (message.getUserType().equals(paymentStatus)) messageCount++;
                    }


                    if (messageCount != 0) showMessageIndicator();



                }
            }

            @Override
            public void onFailure(Call<List<MessageModel>> call, Throwable t) {
                final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Please try again", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        });

    }

    private void showMessageIndicator() {

        int count = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).getInt("messageCount",0);

        if (messageCount > count){
            Log.d("MessageCount", String.valueOf(messageCount-count));
            message_IV.setBadgeValue(messageCount-count)
                    .setBadgeTextSize(12)
                    .setMaxBadgeValue(99)
                    .setBadgeColor(getResources().getColor(R.color.black))
                    .setBadgePosition(BadgePosition.TOP_RIGHT)
                    .setBadgeTextColor(getResources().getColor(R.color.white))
//                    .setBadgeBackground(getResources().getDrawable(R.drawable.badge))
                    .setBadgeTextStyle(Typeface.BOLD)
                    .setShowCounter(true)
                    .setBadgePadding(3);
        }else {
            message_IV.setBadgeValue(0);

        }

    }

    private void getJobs() {
        Call<List<JobModel>> call = mJobApi.getJobs();
        call.enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                progressIndicator.setVisibility(View.GONE);
                if (!response.isSuccessful()){
                    final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Error fetching jobs. Try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadContents();
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();

                }else {
                    List<JobModel> jobs =response.body();

                    initialiseJobsRV(jobs);

                }
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {
                final Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Please try again", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        });

    }

    private void initialiseJobsRV(List<JobModel> jobs) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        jobs_RV.setLayoutManager(layoutManager);
        JobsAdapter jobsAdapter = new JobsAdapter(this, jobs);
        jobs_RV.setAdapter(jobsAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        showMessageIndicator();
    }

    private Boolean isLogin() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d("Phone",currentUser.getPhoneNumber());
            userPhone = currentUser.getPhoneNumber().substring(3);
        }
        return currentUser != null;
    }

    private void getUser() {

        Call<List<UserModel>> call = mUserApi.login(userPhone);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (!response.isSuccessful()) {
                    signOut();
                } else {
                    if (response.body().size() >0){
                        UserModel user = response.body().get(0);
                        paymentStatus = user.getPaymentStatus();
                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).edit();
                        editor.putString("paymentStatus",paymentStatus);
                        editor.apply();

                        fcmSubscribe();

                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void logout(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,CoverActivity.class));
        finish();
    }

    public void gotoMessages(View view) {
        startActivity(new Intent(MainActivity.this,MessageActivity.class));

    }

    private void fcmSubscribe() {

        FirebaseMessaging.getInstance().subscribeToTopic("global");

        if (paymentStatus.equals("1")) FirebaseMessaging.getInstance().subscribeToTopic("paid_user");
        else FirebaseMessaging.getInstance().subscribeToTopic("unpaid_user");


    }
}