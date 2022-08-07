package com.sharkit.nextmonday.main_menu.diary.enums.impl;

import android.content.Context;

import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;

public interface IActionNotateType {
    String getName(Context context);
    void moveToFile(Context context, Notate notate);
    Long createTemplate(NextMondayDatabase db);
}
