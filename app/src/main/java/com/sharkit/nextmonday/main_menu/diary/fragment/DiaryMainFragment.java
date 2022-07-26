package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_CALENDAR;
import static com.sharkit.nextmonday.main_menu.diary.fragment.DayInfoTransformer.toDayName;
import static com.sharkit.nextmonday.main_menu.diary.fragment.DayInfoTransformer.toMonthName;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.DiaryMainListAdapter;
import com.sharkit.nextmonday.main_menu.diary.domain.DayInfo;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DiaryMainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_main, container, false);
        final WidgetContainer.DiaryMainWidget widget = WidgetContainer.newInstance(view).getDiaryMainWidget();
        final NextMondayDatabase db = NextMondayDatabase.getInstance();
        final List<DayInfo> daysInfo = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();

        Optional.ofNullable(getArguments())
                .filter(Objects::nonNull)
                .ifPresent(arg -> calendar.setTimeInMillis(arg.getLong(DIARY_CALENDAR)));

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        for (int i = 0; i < 7; i++) {

            daysInfo.add(DayInfo.builder()
                    .dayOfWeek(toDayName(calendar.get(Calendar.DAY_OF_WEEK)))
                    .month(toMonthName(calendar.get(Calendar.MONTH)))
                    .dayNumber(calendar.get(Calendar.DATE))
                    .dataTime(calendar.getTimeInMillis())
                    .diaryTasks(toDiaryTasks(db.dairyTaskDAO()
                            .findAllByDate(DateFormat.getDateInstance().format(calendar.getTime()))))
                    .build());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        widget.getExpandableListView().setAdapter(new DiaryMainListAdapter(daysInfo, getContext()));

        return view;
    }
}
