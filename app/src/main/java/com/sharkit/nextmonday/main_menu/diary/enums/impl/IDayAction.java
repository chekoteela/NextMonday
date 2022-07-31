package com.sharkit.nextmonday.main_menu.diary.enums.impl;

import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;

public interface IDayAction {

    void repeat(DiaryTask diaryTask, int calendarDay);
}
