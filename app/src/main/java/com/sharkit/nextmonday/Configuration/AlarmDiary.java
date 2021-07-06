package com.sharkit.nextmonday.Configuration;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sharkit.nextmonday.MainMenu;
import com.sharkit.nextmonday.R;

import static com.sharkit.nextmonday.Configuration.ChannelNotification.CHANNEL_1_ID;

public class AlarmDiary extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
        Bitmap imageDiary = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_diary);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo_white)
                .setLargeIcon(imageDiary)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setSummaryText(intent.getStringExtra("part of the Next Monday"))
                        .setBigContentTitle(intent.getStringExtra("text of target"))
                        .bigText(intent.getStringExtra("description")))
                .setContentTitle("У вас есть невыполненные задачи")
                .setLights(context.getColor(R.color.color_button), 5000, 1000)
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
