package com.sharkit.nextmonday.service.calculator.find_food;

import android.view.View;

import com.sharkit.nextmonday.entity.calculator.PFC;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ChildItemService implements LayoutService {
    private final PFC pfc;

    public ChildItemService(PFC pfc) {
        this.pfc = pfc;
    }

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
