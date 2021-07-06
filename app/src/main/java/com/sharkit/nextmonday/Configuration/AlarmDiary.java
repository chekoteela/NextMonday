package com.sharkit.nextmonday.Configuration;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sharkit.nextmonday.MainMenu;
import com.sharkit.nextmonday.R;

import static com.sharkit.nextmonday.Configuration.ChannelNotification.CHANNEL_1_ID;

public class AlarmDiary extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        intent = new Intent(context, MainMenu.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,1, intent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo_white)
                .setContentTitle("Next Monday")
                .setContentText("Target")
                .setLights(context.getColor(R.color.color_button),5000,1000)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setColor(context.getColor(R.color.color_button))
                .build();
        managerCompat.notify(1, notification);
    }
}
