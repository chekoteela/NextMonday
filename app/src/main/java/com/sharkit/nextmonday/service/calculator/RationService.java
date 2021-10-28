package com.sharkit.nextmonday.service.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class RationService implements LayoutService {
    private final String dateRation;
    private TextView date;
    private ExpandableListView listView;
    private Button createMeal;

    public RationService(String dateRation) {
        this.dateRation = dateRation;
    }

    @Override
    public LayoutService writeToField() {
        date.setText(dateRation);
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
        listView = root.findViewById(R.id.ration_xml);
        createMeal = root.findViewById(R.id.create_meal_xml);
        date = root.findViewById(R.id.date_xml);
        return this;
    }
}
