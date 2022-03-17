package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.RelativeLayout;

import com.sharkit.nextmonday.R;

public class RelativeLayoutWidget {

    private final View view;

    public RelativeLayoutWidget(View view) {
        this.view = view;
    }

    public RelativeLayout getChild(){
        return view.findViewById(R.id.child_item_xml);
    }
}
