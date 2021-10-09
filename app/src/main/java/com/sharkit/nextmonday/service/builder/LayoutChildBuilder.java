package com.sharkit.nextmonday.service.builder;

import android.view.View;

import com.sharkit.nextmonday.service.diary.main_diary_service.ChildService;
import com.sharkit.nextmonday.service.diary.main_diary_service.ParentService;

public interface LayoutChildBuilder {
    LayoutChildBuilder writeToField();
    LayoutChildBuilder findById(View root);
    LayoutChildBuilder setAdaptive();
    LayoutChildBuilder activity();
    ChildService build();
}
