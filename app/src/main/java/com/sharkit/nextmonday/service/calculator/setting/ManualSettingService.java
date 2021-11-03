package com.sharkit.nextmonday.service.calculator.setting;

import android.view.View;

import com.sharkit.nextmonday.service.builder.LayoutService;

public class ManualSettingService implements LayoutService {
    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        return this;
    }

    @Override
    public LayoutService findById(View root) {

        return this;
    }
}
