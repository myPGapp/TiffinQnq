package com.tiffin.tiffinqnq.SendNotification;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;
import com.tiffin.tiffinqnq.FirebaseMessagingService;

public class MessageClass extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
