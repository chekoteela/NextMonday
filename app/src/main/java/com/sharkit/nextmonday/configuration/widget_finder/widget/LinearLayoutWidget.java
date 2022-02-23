package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.LinearLayout;

import com.sharkit.nextmonday.R;

public class LinearLayoutWidget {

    private final View view;

    public LinearLayoutWidget(View view) {
        this.view = view;
    }

    public LinearLayout getCheckBoxList(){
        return view.findViewById(R.id.checkbox_list_xml);
    }
}
