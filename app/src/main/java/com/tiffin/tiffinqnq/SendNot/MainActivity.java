package com.tiffin.tiffinqnq.SendNot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tiffin.tiffinqnq.R;

public class MainActivity extends AppCompatActivity {
Button send;
EditText title,msg;
String title1,message;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        title=findViewById(R.id.edttitle);
        msg=findViewById(R.id.edtmessage);
        send=findViewById(R.id.senduser);

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if(task.isSuccessful()){
//                            return;
//                        }
//                        String token=task.getResult();
//                        System.out.println("TOKEN" +token);
//                    }
//                });
        title1=title.getText().toString().trim();
        message=msg.getText().toString().trim();
        {
//                    FCMSend.pushNotification(MainActivity.this,"e0xWa-iGRvyYqwXEej9AtG:APA91bGNcSh1Vi_CQvyXLYh9dx7AWLFb9yFIrN0s7VYUbuZbq_ipxMPykr6C77xKODUmOlc3sl-Aj3pA75EG9CGOCbkq7FjfI0VotuhntcuIZdgYiEwV5ZWpb8TumCePgcvborvExBsD",title1,message);
            FCMSend.pushNotification(MainActivity.this,"e0xWa-iGRvyYqwXEej9AtG:APA91bGNcSh1Vi_CQvyXLYh9dx7AWLFb9yFIrN0s7VYUbuZbq_ipxMPykr6C77xKODUmOlc3sl-Aj3pA75EG9CGOCbkq7FjfI0VotuhntcuIZdgYiEwV5ZWpb8TumCePgcvborvExBsD","title1","message");

        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title1=title.getText().toString().trim();
                message=msg.getText().toString().trim();
                if(!title1.equals("")&& !message.equals("")){
//                    FCMSend.pushNotification(MainActivity.this,"e0xWa-iGRvyYqwXEej9AtG:APA91bGNcSh1Vi_CQvyXLYh9dx7AWLFb9yFIrN0s7VYUbuZbq_ipxMPykr6C77xKODUmOlc3sl-Aj3pA75EG9CGOCbkq7FjfI0VotuhntcuIZdgYiEwV5ZWpb8TumCePgcvborvExBsD",title1,message);
                    FCMSend.pushNotification(MainActivity.this,"e0xWa-iGRvyYqwXEej9AtG:APA91bGNcSh1Vi_CQvyXLYh9dx7AWLFb9yFIrN0s7VYUbuZbq_ipxMPykr6C77xKODUmOlc3sl-Aj3pA75EG9CGOCbkq7FjfI0VotuhntcuIZdgYiEwV5ZWpb8TumCePgcvborvExBsD",title1,message);

                }






            }
        });

    }
}