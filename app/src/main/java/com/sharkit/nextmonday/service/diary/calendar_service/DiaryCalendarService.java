package com.sharkit.nextmonday.service.diary.calendar_service;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_MAIN_DIARY_LIST;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Calendar;

public class DiaryCalendarService implements LayoutService {
    private CalendarView calendarView;
    private Context context;
    private AdView adView;

    @SuppressLint("SetTextI18n")
    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        adView = root.findViewById(R.id.adView);
        calendarView = root.findViewById(R.id.calendar_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        Configuration.showAdView(adView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            Bundle bundle = new Bundle();
            bundle.putLong(DATE_FOR_MAIN_DIARY_LIST, calendar.getTimeInMillis());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary, bundle);
        });
        return this;
    }
}
