package com.sharkit.nextmonday.impl.days;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.impl.WriteToDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class Monday implements WriteToDB {
    @Override
    public void writeToDB(MyTarget target, Map<String, Boolean> mapRepeat, TargetData targetData, Calendar calendar) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Log.d("TAGA", dateFormat.format(calendar.getTimeInMillis()) + " monday");

        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && mapRepeat.get("Monday").equals(true)) {
            target.setTime_alarm(calendar.getTimeInMillis() + 604800000);
            target.setDate(dateFormat.format(calendar.getTimeInMillis() + 604800000));
            try {
                targetData.addNewTarget(target);
            } catch (SQLiteConstraintException ignored) {
            }
        } else if (mapRepeat.get("Monday").equals(true)) {
            target.setDate(dateFormat.format(calendar.getTimeInMillis()));
            target.setTime_alarm(calendar.getTimeInMillis());
            try {
                targetData.addNewTarget(target);
            } catch (SQLiteConstraintException ignored) {
            }
        }
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        if (Calendar.getInstance().getTimeInMillis() + 604800000 > calendar.getTimeInMillis()) {
            new Tuesday().writeToDB(target, mapRepeat, targetData, calendar);
        }
    }
}
