package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sharkit.nextmonday.R;

public class SwitchWidget {

    private final View view;

    public SwitchWidget(View view){
        this.view = view;
    }

    public SwitchMaterial getTakeTime(){
        return view.findViewById(R.id.take_time_xml);
    }

    public SwitchMaterial getRepeat(){
        return view.findViewById(R.id.repeat_xml);
    }

}
