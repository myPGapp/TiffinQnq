package com.tiffin.tiffinqnq;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.CommonNotificationBuilder;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
String text;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        text="data o";

        super.onMessageReceived(remoteMessage);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                r.setLooping(false);

            }
//        Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
//        long[] pattern = {100, 300, 300, 300};
//        v.vibrate(pattern, -1);
        //int resourceImage = getResources().getIdentifier(Objects.requireNonNull(remoteMessage.getNotification()).getIcon(), "drawable", getPackageName());
        String channelID = "Your_channel_id";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);
                builder
                .setSmallIcon(R.drawable.ic_baseline_call_24)
                .setContentTitle("title")
                .setContentText("msg")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity2.class);
//        Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
//        resultIntent.putExtra("data",text);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel =null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationchannel=notificationManager.getNotificationChannel(channelID);
            if(notificationchannel==null){
                int importance=NotificationManager.IMPORTANCE_HIGH;
                notificationChannel=new NotificationChannel(channelID,"good",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            //NotificationChannel notificationchannel= notificationManager.getNotificationChannel(channelId);
//            builder.setSmallIcon(resourceImage);
//            builder.setContentTitle(remoteMessage.getNotification().getTitle());
//            //builder.setContentTitle("hello");
//            builder.setContentText(remoteMessage.getNotification().getBody());
//            //builder.setContentText("ok");
//            builder.setContentIntent(pendingIntent);
            //builder.setStyle(new NotificationCompat.BigTextStyle().bigText("ok"));
//            builder.setAutoCancel(true);
//            builder.setPriority(NotificationCompat.PRIORITY_MAX);
//            builder.setChannelId(channelID);
//            CharSequence name = getString(Integer.parseInt("channel_name"));
//            String description = getString(Integer.parseInt("channel_description"));
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = notificationManager.getNotificationChannel(channelID);
//            //notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            builder.setChannelId(channelID);
            //notificationManager.notify(100,builder.build());
            System.out.println("okk");

        }
        notificationManager.notify(0, builder.build());

    }
}

