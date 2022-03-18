package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.CalendarView;

import com.sharkit.nextmonday.R;

public class CalendarWidget {

    private final View view;

    public CalendarWidget(View view) {
        this.view = view;
    }

    public CalendarView getCalendar(){
        return view.findViewById(R.id.calendar_xml);
    }
}
