package com.tiffin.tiffinqnq.SendNotification;

import android.app.Notification;

public class PushNotification {
     private  NotificationData notification;
        private     String to;

    public PushNotification(NotificationData notification, String to) {
        this.notification = notification;
        this.to = to;
    }

    public NotificationData getNotification() {
        return notification;
    }

    public void setNotification(NotificationData notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
