package com.sharkit.nextmonday.diary.enums;

import android.content.Context;

import com.sharkit.nextmonday.diary.entity.DiaryTask;

import java.util.Calendar;

public interface RepeaterOfTask {
    void repeat(int dayOfWeek, Calendar calendar, DiaryTask diaryTask, Context context);
}
