package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.Switch;

import com.sharkit.nextmonday.R;

public class SwitchWidget {

    private final View view;

    public SwitchWidget(View view){
        this.view = view;
    }

    public Switch getTakeTime(){
        return view.findViewById(R.id.take_time_xml);
    }

    public Switch getRepeat(){
        return view.findViewById(R.id.repeat_xml);
    }

}
