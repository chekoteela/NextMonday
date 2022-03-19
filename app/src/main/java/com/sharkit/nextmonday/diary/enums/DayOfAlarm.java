package com.sharkit.nextmonday.diary.enums;

import static com.sharkit.nextmonday.diary.transformer.DiaryTransformer.toNewDiaryTask;

import android.content.Context;

import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

import java.util.Calendar;

public enum DayOfAlarm implements RepeaterOfTask {

    MONDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    },
    TUESDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    },
    WEDNESDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    },
    THURSDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    },
    FRIDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    },
    SATURDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    },
    SUNDAY {
        @Override
        public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context) {
            repeat(dayOfWeek, calendar, diaryTask, context);
        }
    };

    public void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context){
        while (calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        DiaryTaskRepo.getInstance(context).create(toNewDiaryTask(diaryTask, calendar));
    }
}
