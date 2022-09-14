package com.sharkit.nextmonday.configuration.utils.notification;

import static com.sharkit.nextmonday.configuration.utils.notification.Notification.CHANNEL_1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ChannelNotification extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    CHANNEL_1,
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(CHANNEL_1);
            final NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
        }
}
