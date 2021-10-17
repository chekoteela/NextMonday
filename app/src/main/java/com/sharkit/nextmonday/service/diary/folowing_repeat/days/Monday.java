package com.sharkit.nextmonday.service.diary.folowing_repeat.days;

import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.MONDAY;
import static com.sharkit.nextmonday.configuration.constant.UserSetting.REPEAT_WEEK;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.service.diary.folowing_repeat.WriteToDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class Monday implements WriteToDB {
    public static final String TODAY = MONDAY;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void writeToDB(TargetDiary targetDiary, Map<String, Boolean> mapRepeats, TargetDataService service, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && Objects.requireNonNull(mapRepeats.get(TODAY)).equals(true)) {
            targetDiary.setTimeForAlarm(calendar.getTimeInMillis() + 604800000);
            targetDiary.setDate(dateFormat.format(calendar.getTimeInMillis() + 604800000));
            try {
                service.create(targetDiary);
            } catch (SQLiteConstraintException ignored) {
            }
        } else if (Objects.requireNonNull(mapRepeats.get(TODAY)).equals(true)) {
            targetDiary.setDate(dateFormat.format(calendar.getTimeInMillis()));
            targetDiary.setTimeForAlarm(calendar.getTimeInMillis());
            try {
                service.create(targetDiary);
            } catch (SQLiteConstraintException ignored) {
            }
        }
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        if (Calendar.getInstance().getTimeInMillis() + 604800000 * REPEAT_WEEK> calendar.getTimeInMillis()) {
            new Tuesday().writeToDB(targetDiary, mapRepeats, service, calendar);
        }
    }
}
