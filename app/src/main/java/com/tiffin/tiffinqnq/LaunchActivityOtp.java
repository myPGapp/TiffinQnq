package com.tiffin.tiffinqnq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tiffin.tiffinqnq.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LaunchActivityOtp extends AppCompatActivity {
    FirebaseAuth auth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_otp);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("....."+user);


    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(LaunchActivityOtp.this, MainActivity.class));
            finish();
        }
        startActivity(new Intent(LaunchActivityOtp.this, PhoneOTP1.class));
        finish();
    }
}