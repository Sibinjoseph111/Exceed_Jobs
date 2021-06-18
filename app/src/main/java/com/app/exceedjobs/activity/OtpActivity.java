package com.app.exceedjobs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.app.exceedjobs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    //const
    private static final String TAG = "OTP LOGIN";

    //var
    private String phone, mVerificationId, code;
    private FirebaseAuth mAuth;
    private Boolean signinProgress = false;

    //widgets
    private EditText otp1_ET, otp2_ET, otp3_ET, otp4_ET, otp5_ET, otp6_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phone = getIntent().getStringExtra("phone");
        mAuth = FirebaseAuth.getInstance();


        otp1_ET = findViewById(R.id.otp1_ET);
        otp2_ET = findViewById(R.id.otp2_ET);
        otp3_ET = findViewById(R.id.otp3_ET);
        otp4_ET = findViewById(R.id.otp4_ET);
        otp5_ET = findViewById(R.id.otp5_ET);
        otp6_ET = findViewById(R.id.otp6_ET);

        otp1_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() ==1 ) otp2_ET.requestFocus();
            }
        });

        otp2_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp1_ET.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp1_ET.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() ==1 ) otp3_ET.requestFocus();
            }
        });

        otp2_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
               if (otp1_ET.getText().toString().length() == 0) otp1_ET.requestFocus();
            }
        });

        otp3_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp2_ET.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp2_ET.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() ==1 ) otp4_ET.requestFocus();
            }
        });

        otp3_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (otp2_ET.getText().toString().length() == 0) otp2_ET.requestFocus();
            }
        });

        otp4_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp3_ET.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp3_ET.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() ==1 ) otp5_ET.requestFocus();
            }
        });

        otp4_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (otp3_ET.getText().toString().length() == 0) otp3_ET.requestFocus();
            }
        });

        otp5_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp4_ET.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() ==1 ) otp6_ET.requestFocus();
            }
        });

        otp5_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (otp4_ET.getText().toString().length() == 0) otp4_ET.requestFocus();
            }
        });

        otp6_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) otp5_ET.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                code = otp1_ET.getText().toString() + otp2_ET.getText().toString() + otp3_ET.getText().toString() + otp4_ET.getText().toString() + otp5_ET.getText().toString() + otp6_ET.getText().toString() ;
                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });

        otp6_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (otp5_ET.getText().toString().length() == 0) otp5_ET.requestFocus();
            }
        });

        sendOTP();

    }

    private void sendOTP() {
        Log.d("Phone",phone);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                OtpActivity.this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d(TAG, "onVerificationCompleted:" + credential.getSmsCode());

//            String code = credential.getSmsCode();
//            if (code != null) {
//                Log.d("SMS",code +"::");
                setOtpText(credential);
//            }
//            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
                Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
                        Snackbar.LENGTH_INDEFINITE).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
                Snackbar.make(findViewById(android.R.id.content), e.getMessage(),
                        Snackbar.LENGTH_INDEFINITE).show();
            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {

            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;

            Snackbar.make(findViewById(android.R.id.content), "Code sent",
                    Snackbar.LENGTH_LONG).show();
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        signinProgress = true;
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
//                            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).edit();
//                            editor.putString("userPhone",user.getPhoneNumber());
//                            editor.apply();

                            startActivity(new Intent(OtpActivity.this,MainActivity.class));
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Snackbar.make(findViewById(android.R.id.content), "Invalid code. Try again",
                                        Snackbar.LENGTH_INDEFINITE).show();
                            }
//                            Snackbar.make(findViewById(android.R.id.content), task.getException().getMessage(),
//                                    Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                });
    }

    private void setOtpText(PhoneAuthCredential credential) {
        String SMScode = credential.getSmsCode();
        if(SMScode !=null) {
            otp1_ET.setText(String.valueOf(SMScode.charAt(0)));
            otp2_ET.setText(String.valueOf(SMScode.charAt(1)));
            otp3_ET.setText(String.valueOf(SMScode.charAt(2)));
            otp4_ET.setText(String.valueOf(SMScode.charAt(3)));
            otp5_ET.setText(String.valueOf(SMScode.charAt(4)));
            otp6_ET.setText(String.valueOf(SMScode.charAt(5)));
        }
        if (!signinProgress) signInWithPhoneAuthCredential(credential);
    }

}