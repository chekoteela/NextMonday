package com.sharkit.nextmonday.service.calculator.ration_service;

import android.view.View;
import android.widget.ImageView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ParentItemService implements LayoutService {
    private ImageView addFood;

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
        addFood = root.findViewById(R.id.create_my_food_xml);
        return this;
    }
}
