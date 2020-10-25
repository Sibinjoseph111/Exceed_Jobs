package com.app.exceedjobs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.exceedjobs.R;

public class CoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
    }

    public void gotoSignup(View view) {
        startActivity(new Intent(this,SignupActivity.class));
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}