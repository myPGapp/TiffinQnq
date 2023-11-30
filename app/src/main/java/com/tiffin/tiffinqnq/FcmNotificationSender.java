package com.tiffin.tiffinqnq;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.inappmessaging.MessagesProto;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FcmNotificationSender {
    String userToken,title,body;
            Activity activity;
            Context context;
    //private final String postUrl="https://fcm.googleapis.com/fcm/send";
    private final String postUrl= "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey="AAAAEq7a_sU:APA91bFkxcuAa9IGdDRZ8R9QJ1RcIVI19-zfmtbEf-Bo5LlAvhQCWxnncz0OPU3oT0LQLoBvQdiNWi2zYfNg4ejx2RP0yejWlP98QAUAtk8W0bWcY1biFwG1gjvyIUS5ERA-fXZN2tMc";


    public FcmNotificationSender(String userToken, Context context, String title, String body, Activity activity) {
        this.userToken=userToken;
        this.context=context;
        this.title=title;
        this.body=body;
        this.activity=activity;
    }

    public void SendNotifications(){
//        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JSONObject mobject=new JSONObject();
        try{
            mobject.put("to",userToken);
            JSONObject notiObject=new JSONObject();
            notiObject.put("title",title);
            notiObject.put("body",body);
            notiObject.put("icon",R.drawable.ic_baseline_call_24);
            mobject.put("notification",notiObject);
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, postUrl, mobject, response -> {

            }, error -> {

            }){
                @Override
                public Map<String,String> getHeaders() {
                    Map<String,String> header=new HashMap<>();
                    header.put("Content-Type","application/json");
                    header.put("Authorization","key="+fcmServerKey);
                    return header;
                }



            };
            System.out.println("usertt"+userToken);
            requestQueue.add(request);


        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
