package com.tiffin.tiffinqnq.Notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tiffin.tiffinqnq.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotSend extends AppCompatActivity {
Button btnsend;
String message;
String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_send);
        btnsend=findViewById(R.id.btnsend);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                token=s;
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage( message);
            }
        });
    }

    private void SendMessage(String message) {
        try{
JSONObject jsonObject=new JSONObject();
JSONObject notificationobject=new JSONObject();
notificationobject.put("title","title");
notificationobject.put("body","message");
JSONObject dataobj=new JSONObject();
dataobj.put("name","name");
jsonObject.put("notification",notificationobject);
jsonObject.put("data",dataobj);
jsonObject.put("to",token);
callApi(jsonObject);

        }
        catch (Exception e){

        }
    }
    void callApi(JSONObject jsonObject){
        MediaType JSON=MediaType.get("application/json; charset=utf-8");
        OkHttpClient client=new OkHttpClient();
        String url="https://fcm.googleapis.com/fcm/send";
        RequestBody body=RequestBody.create(jsonObject.toString(),JSON);
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization","Bearer AAAAEq7a_sU:APA91bFkxcuAa9IGdDRZ8R9QJ1RcIVI19-zfmtbEf-Bo5LlAvhQCWxnncz0OPU3oT0LQLoBvQdiNWi2zYfNg4ejx2RP0yejWlP98QAUAtk8W0bWcY1biFwG1gjvyIUS5ERA-fXZN2tMc")
                .build();
client.newCall(request).enqueue(new Callback() {
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {

    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

    }
});

    }
}