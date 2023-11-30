package com.tiffin.tiffinqnq;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Context;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity2 extends AppCompatActivity {

    EditText edtmessage, edttoken,edttitle;
    Button sendall,senduser,send1;
    String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        edtmessage = findViewById(R.id.edtmessage);
        edttitle = findViewById(R.id.edttitle);
        edttoken = findViewById(R.id.edttoken);
        senduser=findViewById(R.id.senduser);
        sendall=findViewById(R.id.sendAll);
        send1=findViewById(R.id.send1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity2.this,new String[]{
                        Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }


        FirebaseMessaging.getInstance().subscribeToTopic("all");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("token:", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                         token = task.getResult();
                        Toast.makeText(MainActivity2.this, token, Toast.LENGTH_SHORT).show();
                        System.out.println("tokeen "+token);
                    }
                });


        sendall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edttitle.getText().toString().isEmpty()|| !edtmessage.getText().toString().isEmpty())
                {
                    FcmNotificationSender notificationSender=new FcmNotificationSender("/topics/all", getApplicationContext(),
                            edttitle.getText().toString().trim(),edtmessage.getText().toString().trim(),MainActivity2.this);
                    notificationSender.SendNotifications();
                    System.out.println("sendall");
                }
                else
                {
                    Toast.makeText(MainActivity2.this, "enter fields", Toast.LENGTH_SHORT).show();

                }

            }
        });
        senduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edttitle.getText().toString().isEmpty()|| !edtmessage.getText().toString().isEmpty()|| !edttoken.getText().toString().isEmpty()){
                    System.out.println("tokeenn "+token);
                    FcmNotificationSender notificationSender=new FcmNotificationSender(edttoken.getText().toString().trim(),getApplicationContext(),
                            edttitle.getText().toString(),edtmessage.getText().toString(),MainActivity2.this);
                    notificationSender.SendNotifications();
                    System.out.println("senduser");
                }else{
                    Toast.makeText(MainActivity2.this, "plz fil all", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}