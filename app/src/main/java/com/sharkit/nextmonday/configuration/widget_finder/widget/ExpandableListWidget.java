package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.R;

public class ExpandableListWidget {

    private final View view;

    public ExpandableListWidget(View view){
        this.view = view;
    }

    public ExpandableListView getExpandableListView(){
        return view.findViewById(R.id.expandable_list_xml);
    }

}
