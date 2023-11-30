package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tiffin.tiffinqnq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class OtpVerify extends AppCompatActivity {
    TextView tvPhone,resendtimer;
    EditText n1,n2,n3,n4,n5,n6;
    Button btnVerify,btnResend;
    String verificationId;
    ProgressBar progressBar2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
//        editTextInput();
        tvPhone=findViewById(R.id.tvPhone);
        progressBar2=findViewById(R.id.progressBar2);
        n1=findViewById(R.id.n1);
        n2=findViewById(R.id.n2);
        n3=findViewById(R.id.n3);
        n4=findViewById(R.id.n4);
        n5=findViewById(R.id.n5);
        n6=findViewById(R.id.n6);
        btnResend=findViewById(R.id.btnResend);
        resendtimer=findViewById(R.id.resendtimer);
        btnVerify=findViewById(R.id.btnVerify);

        progressBar2.setVisibility(View.INVISIBLE);
        tvPhone.setText(String.format("+91"+getIntent().getStringExtra("phone")));
        verificationId=getIntent().getStringExtra("verificationId");
        editTextInput();
/*
        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                resendtimer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                resendtimer.setText("00:00:00");
            }
        }.start();
*/
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(OtpVerify.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar2.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.INVISIBLE);
                if(n1.getText().toString().trim().isEmpty()||
                        n2.getText().toString().trim().isEmpty()||
                        n3.getText().toString().trim().isEmpty()||
                        n4.getText().toString().trim().isEmpty()||
                        n5.getText().toString().trim().isEmpty()||
                        n6.getText().toString().trim().isEmpty()){
                    Toast.makeText(OtpVerify.this, "OTP Not Valid", Toast.LENGTH_SHORT).show();

                }else{

                    if(verificationId !=null){
                        String code=n1.getText().toString().trim()+
                                n2.getText().toString().trim()+
                                n3.getText().toString().trim()+
                                n4.getText().toString().trim()+
                                n5.getText().toString().trim()+
                                n6.getText().toString().trim();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth.getInstance().signInWithCredential(credential).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            progressBar2.setVisibility(View.VISIBLE);
                                            btnVerify.setVisibility(View.INVISIBLE);
                                            Toast.makeText(OtpVerify.this, "Welcome to TiffinQnQ", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(OtpVerify.this,OtpUpdateProfile.class);
                                            intent.putExtra("phone", getIntent().getStringExtra("phone"));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();

                                        }else{

                                            progressBar2.setVisibility(View.INVISIBLE);
                                            btnVerify.setVisibility(View.VISIBLE);
                                            Toast.makeText(OtpVerify.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                    }
                }

            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(OtpVerify.this, MainActivity.class));
            finish();
        }
    }
    private void editTextInput() {

        n1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                 n1.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                n2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                n2.requestFocus();

            }
        });
        n2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                n3.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                n3.requestFocus();

            }
        });
        n3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                n4.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                n4.requestFocus();
            }
        });
        n4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                n5.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                n5.requestFocus();
            }
        });
        n5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                n6.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                n6.requestFocus();
            }
        });

    }
}