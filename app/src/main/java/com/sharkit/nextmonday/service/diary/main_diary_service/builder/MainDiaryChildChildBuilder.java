package com.sharkit.nextmonday.service.diary.main_diary_service.builder;

import android.view.View;

import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.service.builder.LayoutChildBuilder;
import com.sharkit.nextmonday.service.diary.main_diary_service.ChildService;

import java.util.ArrayList;

public class MainDiaryChildChildBuilder implements LayoutChildBuilder {
    private final ChildService childService;

    public MainDiaryChildChildBuilder(ChildItemTargetDTO targetDTO) {
        childService = new ChildService(targetDTO);
    }

    @Override
    public LayoutChildBuilder writeToField() {
        childService.writeToField();
        return this;
    }

    @Override
    public LayoutChildBuilder findById(View root) {
        childService.findById(root);
        return this;
    }

    @Override
    public LayoutChildBuilder setAdaptive() {
        childService.setAdaptive();
        return this;
    }

    @Override
    public LayoutChildBuilder activity() {
        childService.activity();
        return this;
    }

    @Override
    public ChildService build() {
        return childService;
    }
}
