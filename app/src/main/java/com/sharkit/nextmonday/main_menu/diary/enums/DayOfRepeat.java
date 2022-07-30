package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.NextMondayActivity.getContext;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTask;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTaskDTO;

import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IDayAction;

import java.io.Serializable;
import java.util.Calendar;

public enum DayOfRepeat implements IDayAction, Serializable {

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
        final NextMondayDatabase db = NextMondayDatabase.getInstance(getContext());

        calendar.setTimeInMillis(diaryTask.getTimeForRepeat());

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
