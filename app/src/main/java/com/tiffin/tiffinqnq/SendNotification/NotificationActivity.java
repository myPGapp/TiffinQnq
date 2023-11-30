package com.tiffin.tiffinqnq.SendNotification;

import static com.tiffin.tiffinqnq.SendNotification.ValuesClass.TO;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tiffin.tiffinqnq.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText title,message;
    Button btnsend;
    String title1,message1;
@SuppressLint("MissingInflatedId")
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    message=findViewById(R.id.edtmessage);
    title=findViewById(R.id.edttitle);
    btnsend=findViewById(R.id.btnsend);
    firebaseAuth=FirebaseAuth.getInstance();
    firebaseFirestore=FirebaseFirestore.getInstance();
    btnsend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//             title1=title.getText().toString();
            title1 = "ok";
//            message1=message.getText().toString();
            message1="hello";
            sendNoti();
        }
    });
    msg();
    }

    private void sendNoti() {
    ApiUtils.getClients().sendNOtification(new PushNotification(new NotificationData(title1,message1),TO))
            .enqueue(new Callback<PushNotification>() {
                @Override
                public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(NotificationActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NotificationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PushNotification> call, Throwable t) {
                    String m=t.getMessage().toString();
                    Toast.makeText(NotificationActivity.this, m, Toast.LENGTH_SHORT).show();

                }
            });
    }

    private void msg() {
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            FirebaseAuth.getInstance()
                    .signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseMessaging.getInstance()
                                    .subscribeToTopic("All");

                        }
                    });

        }else{
            FirebaseMessaging.getInstance()
                    .subscribeToTopic("All");

        }

        /*
        else{
            FirebaseMessaging.getInstance().getToken()
                    .addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            String userID=firebaseAuth.getCurrentUser().getUid();
                            Toast.makeText(NotificationActivity.this, "done", Toast.LENGTH_SHORT).show();
                            NotificationData notificationData=new NotificationData("",s);
                            FirebaseFirestore.getInstance()
                                    .collection("users").document(userID)
                                    .set(notificationData);

                        }
                    });

        }
        */
    }
}
