package com.sharkit.nextmonday.diary.ui;

import static com.sharkit.nextmonday.diary.transformer.DiaryTransformer.toTaskOfDay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.adapter.DiaryMainListAdapter;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.entity.TaskOfDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryMain extends Fragment {

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_main, container, false);
        Widget widget = Widget.findByView(view);
        Calendar calendar = Calendar.getInstance();

        List<TaskOfDay> taskOfDays = new ArrayList<>();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        for (int i = 0; i < 7; i++) {
            taskOfDays.add(toTaskOfDay(DiaryTaskRepo.getInstance(getContext())
                    .findAllByWeekAndDate(calendar.get(Calendar.WEEK_OF_YEAR),
                            SimpleDateFormat.getDateInstance().format(calendar.getTimeInMillis()))
                    .orElse(null), calendar));

            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        DiaryMainListAdapter adapter = new DiaryMainListAdapter(getContext(), taskOfDays);
        widget.getExpandableList().getExpandableListView().setAdapter(adapter);

        return view;
    }
}
