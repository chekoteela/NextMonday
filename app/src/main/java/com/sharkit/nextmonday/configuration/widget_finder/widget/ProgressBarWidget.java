package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.ProgressBar;

import com.sharkit.nextmonday.R;

public class ProgressBarWidget {

    private final View view;

    public ProgressBarWidget(View view) {
        this.view = view;
    }

    public ProgressBar getCompleteTaskProgress(){
        return view.findViewById(R.id.progress_bar_xml);
    }
}
