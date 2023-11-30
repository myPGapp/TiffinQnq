package com.tiffin.tiffinqnq.SendNot;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMSend {
    public static final String BASE_URL="https://fcm.googleapis.com/fcm/send";
    public static final String SERVER_KEY="key=AAAAEq7a_sU:APA91bFkxcuAa9IGdDRZ8R9QJ1RcIVI19-zfmtbEf-Bo5LlAvhQCWxnncz0OPU3oT0LQLoBvQdiNWi2zYfNg4ejx2RP0yejWlP98QAUAtk8W0bWcY1biFwG1gjvyIUS5ERA-fXZN2tMc";
    public static void pushNotification(Context context, String token, String title, String message){
//        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy().Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        RequestQueue requestQueue=new Volley().newRequestQueue(context);
        try{
            JSONObject json=new JSONObject();
            json.put("to",token);
JSONObject notification=new JSONObject();
        notification.put("title",title);
        notification.put("body",message);
        json.put("notification",notification);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, BASE_URL, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 System.out.println("FCM"+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("Authorization",SERVER_KEY);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);

        }
         catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
