package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.RadioGroup;

import com.sharkit.nextmonday.R;

public class RadioGroupWidget {

    private final View view;

    public RadioGroupWidget(View view) {
        this.view = view;
    }

    public RadioGroup getRepeatRadioGroup(){
        return view.findViewById(R.id.repeat_radio_group_xml);
    }

}
