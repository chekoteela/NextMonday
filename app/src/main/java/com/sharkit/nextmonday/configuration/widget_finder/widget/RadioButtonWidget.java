package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.RadioButton;

import com.sharkit.nextmonday.R;

public class RadioButtonWidget {

    private final View view;

    public RadioButtonWidget(View view) {
        this.view = view;
    }

    public RadioButton getRepeatEveryDay(){
        return view.findViewById(R.id.repeat_everyday_xml);
    }

    public RadioButton getRepeatInSelectedDays(){
        return view.findViewById(R.id.select_day_xml);
    }

}
