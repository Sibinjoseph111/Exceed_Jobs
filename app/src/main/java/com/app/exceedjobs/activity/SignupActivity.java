package com.app.exceedjobs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.exceedjobs.R;
import com.app.exceedjobs.api.UserApi;
import com.app.exceedjobs.model.UserModel;
import com.app.exceedjobs.utility.InternetCheck;
import com.app.exceedjobs.utility.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    //const

    //var
    String name="", phone="", email="", address="";
    private  Snackbar snackbar;
    private UserApi mUserApi;

    //widget
    private TextInputEditText name_ET, phone_ET, email_ET, address_ET;
    private TextInputLayout name_IL, phone_IL, email_IL, address_IL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_ET = findViewById(R.id.signupName_ET);
        name_IL = findViewById(R.id.signupName_IL);
        phone_ET = findViewById(R.id.signupPhone_ET);
        phone_IL = findViewById(R.id.signupPhone_IL);
        email_ET = findViewById(R.id.signupEmail_ET);
        email_IL = findViewById(R.id.signupEmail_IL);
        address_ET = findViewById(R.id.signupAddress_ET);
        address_IL = findViewById(R.id.signupAddress_IL);

        mUserApi = RetrofitClient.getClient().create(UserApi.class);
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
        finish();
    }

    public void register(View view) {

        name = String.valueOf(name_ET.getText());
        phone = String.valueOf(phone_ET.getText());
        email = String.valueOf(email_ET.getText());
        address = String.valueOf(address_ET.getText());

        if (name.length() == 0) name_IL.setError("Enter your name");
        else if (phone.length()<10) phone_IL.setError("Enter valid phone number");
//        else if (email.length() == 0) email_IL.setError("Enter your email address");/
//        else if (address.length() == 0) address_IL.setError("Enter your address");
        else {
            checkInternet();
        }

    }

    private void checkInternet() {
        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {
                if (internet){
                    snackbar =  Snackbar.make(findViewById(android.R.id.content), "Registering user. Please wait", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                    signupUser();

                }else {
                    Log.d("INTERNET","NO INTERNET");
                    snackbar =  Snackbar.make(findViewById(android.R.id.content), "Bad network connection. Try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkInternet();
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }

    private void signupUser() {

        HashMap<String,String> userDetails = new HashMap<>();

        userDetails.put("name",name);
        userDetails.put("email",email);
        userDetails.put("phone",phone);
        userDetails.put("address",address);

        Call<Void> call = mUserApi.signup(userDetails);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    if (response.code() == 409){
                        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Phone number already linked to another user account", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                    }else {
                        snackbar =  Snackbar.make(findViewById(android.R.id.content), "Registration failed. Please try again", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                    }

                }else {
                    gotoOTP(phone);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                snackbar =  Snackbar.make(findViewById(android.R.id.content), "Something went wrong. Please try again", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
        });

    }

    public void gotoOTP(String phone) {
        Intent intent = new Intent(SignupActivity.this,OtpActivity.class);
        intent.putExtra("phone","+91"+phone);
        startActivity(intent);
    }
}