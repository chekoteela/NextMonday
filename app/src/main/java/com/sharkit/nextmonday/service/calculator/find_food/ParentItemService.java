package com.sharkit.nextmonday.service.calculator.find_food;

import android.view.View;

import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ParentItemService implements LayoutService {
    private final GeneralDataPFCDTO generalDataPFCDTO;

    public ParentItemService(GeneralDataPFCDTO generalDataPFCDTO) {
        this.generalDataPFCDTO = generalDataPFCDTO;
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
