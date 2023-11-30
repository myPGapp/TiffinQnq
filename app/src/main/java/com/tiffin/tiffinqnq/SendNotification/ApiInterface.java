package com.tiffin.tiffinqnq.SendNotification;

import static com.tiffin.tiffinqnq.SendNotification.ValuesClass.CONTENT_TYPE;
import static com.tiffin.tiffinqnq.SendNotification.ValuesClass.SERVER_KEY;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization:key="+SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
    @POST("/fcm/send")
    Call<PushNotification> sendNOtification(@Body PushNotification pushNotification);
}
