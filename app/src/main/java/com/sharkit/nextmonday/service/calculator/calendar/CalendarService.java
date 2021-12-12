package com.sharkit.nextmonday.service.calculator.calendar;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_RATION_DATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarService implements LayoutService {
    private CalendarView calendarView;
    private Context context;

    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService activity() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            Bundle bundle = new Bundle();
            bundle.putString(FRAGMENT_RATION_DATE, new SimpleDateFormat(SHOW_DATE_FORMAT).format(calendar.getTimeInMillis()));
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_ration, bundle);
        });
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        calendarView = root.findViewById(R.id.calendar_xml);
        return this;
    }
}
