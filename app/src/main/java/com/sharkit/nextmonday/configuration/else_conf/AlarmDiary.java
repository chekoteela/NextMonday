package com.sharkit.nextmonday.configuration.else_conf;

import static com.sharkit.nextmonday.configuration.constant.Notification.BIG_TEXT;
import static com.sharkit.nextmonday.configuration.constant.Notification.CHANNEL_1;
import static com.sharkit.nextmonday.configuration.constant.Notification.CONTENT_TITLE;
import static com.sharkit.nextmonday.configuration.constant.Notification.SUMMARY_TEXT;
import static com.sharkit.nextmonday.configuration.constant.Notification.UNCOMPLETED_TASK;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sharkit.nextmonday.R;

@SuppressLint("UnspecifiedImmutableFlag")
public class AlarmDiary extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
        Bitmap imageDiary = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_diary);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1)
                .setSmallIcon(R.drawable.logo_white)
                .setLargeIcon(imageDiary)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setSummaryText(intent.getStringExtra(SUMMARY_TEXT))
                        .setBigContentTitle(intent.getStringExtra(CONTENT_TITLE))
                        .bigText(intent.getStringExtra(BIG_TEXT)))
                .setContentTitle(UNCOMPLETED_TASK)
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
