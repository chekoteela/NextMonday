package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTask;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTaskDTO;

import android.util.Log;

import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IDayAction;

import java.text.DateFormat;
import java.util.Calendar;

public enum DayOfRepeat implements IDayAction {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    @Override
    public void repeat(DiaryTask diaryTask, int calendarDay) {
        final Calendar calendar = Calendar.getInstance();
        final NextMondayDatabase db = NextMondayDatabase.getInstance();

        calendar.setTimeInMillis(diaryTask.getTimeForRepeat());

        Log.i("TAGA", DateFormat.getDateInstance().format(calendar.getTime()));
        if (calendar.get(Calendar.DAY_OF_WEEK) == calendarDay) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        } else {
            while (calendar.get(Calendar.DAY_OF_WEEK) != calendarDay) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        }

        DiaryTask newDiaryTask = toDiaryTask(diaryTask, calendar);
        db.dairyTaskDAO().updateRepeat(diaryTask.getId());
        db.dairyTaskDAO().create(toDiaryTaskDTO(newDiaryTask));
    }
}
