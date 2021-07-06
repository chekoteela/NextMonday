package com.sharkit.nextmonday.Configuration;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.sharkit.nextmonday.MainMenu;
import com.sharkit.nextmonday.R;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class Alarm {
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "Next Monday";
    private Context context;
    private Long timeForAlarm;

    public Alarm(Context context, Long timeForAlarm) {
        this.context = context;
        this.timeForAlarm = timeForAlarm;
    }

    public void setAlarm(){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.logo_white)
                .setWhen(timeForAlarm)
                .setContentIntent(pendingIntent)
                .setContentTitle("Title")
                .setContentText("text")
                .setPriority(PRIORITY_HIGH);
        createChannel(notificationManager);
        notificationManager.notify(NOTIFY_ID,builder.build());
    }

    private void createChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
