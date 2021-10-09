package com.sharkit.nextmonday.service.builder;

import android.view.View;

import com.sharkit.nextmonday.service.diary.main_diary_service.ParentService;

public interface LayoutParentBuild {
    LayoutParentBuild writeToField();
    LayoutParentBuild findById(View root);
    LayoutParentBuild setAdaptive();
    LayoutParentBuild activity();
    ParentService build();
}
