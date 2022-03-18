package com.sharkit.nextmonday.diary.ui;

import static com.sharkit.nextmonday.diary.constant.DiaryConstant.DATE_FOR_DIARY_MAIN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

import java.util.Calendar;

public class DiaryCalendar extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_calendar, container, false);
        Widget widget = Widget.findByView(view);

        widget.getCalendarView()
                .getCalendar()
                .setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    Bundle bundle = new Bundle();
                    bundle.putLong(DATE_FOR_DIARY_MAIN, calendar.getTimeInMillis());
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(R.id.navigation_diary_main, bundle);
                });
        return view;
    }
}
