package com.app.exceedjobs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.exceedjobs.R;
import com.app.exceedjobs.api.JobApi;
import com.app.exceedjobs.api.UserApi;
import com.app.exceedjobs.model.UserModel;
import com.app.exceedjobs.utility.InternetCheck;
import com.app.exceedjobs.utility.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //const

    //var
    private UserApi mUserApi;
    private  Snackbar snackbar;

    //widgets
    private TextInputEditText phone_ET;
    private TextInputLayout phone_IL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone_ET = findViewById(R.id.loginPhone_ET);
        phone_IL = findViewById(R.id.loginPhone_IL);

        mUserApi = RetrofitClient.getClient().create(UserApi.class);
    }

    public void gotoSignup(View view) {
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
    }

    public void checkUser(View view) {
        final String phone = String.valueOf(phone_ET.getText());

        if (phone.length() < 10) phone_IL.setError("Enter a valid mobile number");
        else {
            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean internet) {
                    if (internet){
                        snackbar =  Snackbar.make(findViewById(android.R.id.content), "Validating user. Please wait", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                        getUser(phone);

                    }else {
                        Log.d("INTERNET","NO INTERNET");
                        snackbar =  Snackbar.make(findViewById(android.R.id.content), "Bad network connection. Try again", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                    }
                }
            });
        }
    }

    private void getUser(final String phone) {

        Call<List<UserModel>> call = mUserApi.login(phone);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (!response.isSuccessful()) {
                    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Phone number is not linked to any user account. Try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();

                } else {
                    gotoOTP(phone);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                snackbar =  Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Please try again", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        });

    }

    public void gotoOTP(String phone) {

//        String phone = String.valueOf(phone_ET.getText());
//
//        if (phone.length() < 10) phone_IL.setError("Enter a valid mobile number");
//        else {
            Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
            intent.putExtra("phone","+91"+phone);
            startActivity(intent);
//        }
    }
}