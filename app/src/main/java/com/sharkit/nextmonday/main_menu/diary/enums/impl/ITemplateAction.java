package com.sharkit.nextmonday.main_menu.diary.enums.impl;

import android.content.Context;
import android.view.View;

import com.sharkit.nextmonday.main_menu.diary.domain.Notate;

public interface ITemplateAction {

    void create(Context context, Notate notate);

    View getView(Context context);

    void setAction(View view, Notate notate);
}
