package com.sharkit.nextmonday.main_menu.diary.configuration.action;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.configuration.AlarmDiary;

import java.util.Calendar;

@SuppressLint("UnspecifiedImmutableFlag")
public enum Actions implements INotificationAction {

    PERFORM {
        @Override
        public void doAction(final long taskId, final Context context) {
            NextMondayDatabase.getInstance(context).dairyTaskDAO().updateStatus(taskId, true);
        }
    },
    PUT_OFF {
        @Override
        public void doAction(final long taskId, final Context context) {
            final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) taskId, new Intent(context, AlarmDiary.class), 0);
            final long newTime = Calendar.getInstance().getTimeInMillis() + 10000;
            alarmManager.set(AlarmManager.RTC_WAKEUP, newTime, pendingIntent);
            NextMondayDatabase.getInstance(context).dairyTaskDAO().updateTimeOfAlarm(taskId, newTime);
        }
    },
    CANCEL {
        @Override
        public void doAction(final long taskId, final Context context) {
            NextMondayDatabase.getInstance(context).dairyTaskDAO().updateAlarmStatus(taskId, false);
        }
    }

}
