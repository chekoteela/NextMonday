package com.sharkit.nextmonday.main_menu.diary.configuration;

import static com.sharkit.nextmonday.configuration.utils.notification.Notification.DIARY_TASK_ID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.sharkit.nextmonday.main_menu.diary.configuration.action.Actions;

public class DiaryNotificationAction extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final int taskId = intent.getIntExtra(DIARY_TASK_ID, 0);
        final NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        Actions.valueOf(intent.getAction()).doAction(taskId, context);
        managerCompat.cancel(taskId);
    }
}
