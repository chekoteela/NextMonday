package com.sharkit.nextmonday.configuration;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ChannelNotification extends Application {

    public static final String CHANNEL_1_ID = "Ежидневник";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Ежидневник",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Ежидневник");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
        }
}
