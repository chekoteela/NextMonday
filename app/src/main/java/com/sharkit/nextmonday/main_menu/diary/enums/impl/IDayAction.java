package com.sharkit.nextmonday.main_menu.diary.enums.impl;

import android.content.Context;

import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;

public interface IDayAction {

    void repeat(DiaryTask diaryTask, int calendarDay, Context context);
}
