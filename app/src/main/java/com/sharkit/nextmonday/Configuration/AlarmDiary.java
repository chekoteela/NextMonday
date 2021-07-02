package com.sharkit.nextmonday.Configuration;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.sharkit.nextmonday.R;

public class AlarmDiary extends NotificationCompat {
    Context context;
    final String CHANNEL_ID = "1";
    public void Alarm(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("Next Monday")
                .setContentText("У вас не завершеные задачи")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
    private void createNotificationChannel(){
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "NextMonday",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
