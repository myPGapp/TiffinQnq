package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tiffin.tiffinqnq.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

public class PhoneOTP1 extends AppCompatActivity {
    Button btnSend;
    EditText edtPhone;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp1);
        auth=FirebaseAuth.getInstance();
        btnSend=findViewById(R.id.btnSend);
        edtPhone=findViewById(R.id.edtPhone);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhone.getText().toString().trim().isEmpty()){
                    Toast.makeText(PhoneOTP1.this, "Plz enter valid phone number", Toast.LENGTH_SHORT).show();

                }else if(edtPhone.getText().toString().trim().length() !=10){
                    Toast.makeText(PhoneOTP1.this, "Plz enter valid phone number", Toast.LENGTH_SHORT).show();


                }else{
                    otpSend();
                }
            }
        });

    }

    private void otpSend() {
        progressBar.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.INVISIBLE);
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressBar.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneOTP1.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   PhoneAuthProvider.@NonNull ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                progressBar.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneOTP1.this, "OTP Sent Succesfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(PhoneOTP1.this,OtpVerify.class);
                intent.putExtra("phone",edtPhone.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);
                finish();

            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91"+edtPhone.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(PhoneOTP1.this, MainActivity.class));
            finish();
        }
    }
}
