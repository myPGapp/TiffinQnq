package com.tiffin.tiffinqnq.SendNotificationPack;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAEq7a_sU:APA91bFkxcuAa9IGdDRZ8R9QJ1RcIVI19-zfmtbEf-Bo5LlAvhQCWxnncz0OPU3oT0LQLoBvQdiNWi2zYfNg4ejx2RP0yejWlP98QAUAtk8W0bWcY1biFwG1gjvyIUS5ERA-fXZN2tMc" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

