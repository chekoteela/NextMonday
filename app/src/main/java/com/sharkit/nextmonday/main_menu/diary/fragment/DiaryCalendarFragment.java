package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_DAY_OF_WEEK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

import java.util.Calendar;

public class DiaryCalendarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_calendar, container, false);
        final WidgetContainer.DiaryCalendarWidget widget = WidgetContainer.newInstance(view).getDiaryCalendarWidget();

        widget.getCalendarView().setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, dayOfMonth);

            Bundle bundle = new Bundle();

            bundle.putLong(DIARY_DAY_OF_WEEK, calendar.getTimeInMillis());

            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_main, bundle);
        });
        return view;
    }
}
