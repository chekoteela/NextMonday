package com.sharkit.nextmonday.main_menu.diary.enums.impl;

import android.content.Context;

import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;

public interface IActionNotateType {
    String getName(Context context);
    void moveToFile(Context context, Long templateId);
    Long createTemplate(NextMondayDatabase db);
}
