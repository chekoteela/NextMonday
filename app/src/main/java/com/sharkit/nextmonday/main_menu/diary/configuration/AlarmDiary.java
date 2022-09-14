package com.sharkit.nextmonday.main_menu.diary.configuration;

import static com.sharkit.nextmonday.configuration.utils.notification.Notification.BIG_TEXT;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.CHANNEL_1;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.CONTENT_TITLE;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.DIARY_TASK_ID;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.SUMMARY_TEXT;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.UNCOMPLETED_TASK;

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

import com.sharkit.nextmonday.NextMondayActivity;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.diary.configuration.action.Actions;

@SuppressLint("UnspecifiedImmutableFlag")
public class AlarmDiary extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        final NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(context, NextMondayActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        final Bitmap imageDiary = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_diary);
        final int taskId = intent.getIntExtra(DIARY_TASK_ID, 0);

        final Notification notification = new NotificationCompat.Builder(context, CHANNEL_1)
                .setSmallIcon(R.drawable.logotype_next_monday)
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
                .setContentIntent(pendingIntent)
                .setColor(context.getColor(R.color.color_button))
                .addAction(action(context, taskId, Actions.PERFORM, context.getString(R.string.button_already_performed)))
                .addAction(action(context, taskId, Actions.PUT_OFF, context.getString(R.string.button_put_off)))
                .addAction(action(context, taskId, Actions.CANCEL, context.getString(R.string.button_cancel)))
                .build();

        managerCompat.notify(intent.getIntExtra(DIARY_TASK_ID, 0), notification);
    }

    private NotificationCompat.Action action(final Context context, final int taskId, final Actions actions, final String buttonText) {
        final Intent intent = new Intent(context, DiaryNotificationAction.class)
                .setAction(actions.name())
                .putExtra(DIARY_TASK_ID, taskId);
        final PendingIntent cancelPI = PendingIntent.getBroadcast(context, taskId, intent, 0);
        return new NotificationCompat.Action(R.drawable.icon, buttonText, cancelPI);
    }
}
