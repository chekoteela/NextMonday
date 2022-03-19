package com.sharkit.nextmonday.diary.service;

import static com.sharkit.nextmonday.diary.transformer.DateTransformer.toDayOfWeek;
import static com.sharkit.nextmonday.diary.transformer.DiaryTransformer.toTaskOfDay;

import android.content.Context;

import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.adapter.DiaryMainListAdapter;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.entity.TaskOfDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DiaryMainService {
    private final Context context;
    private final Calendar calendar;
    private final Widget widget;

    private DiaryMainService(Context context, Calendar calendar, Widget widget) {
        this.context = context;
        this.calendar = calendar;
        this.widget = widget;
        getWeekTasks();
    }

    public static DiaryMainService getInstance(Context context, Calendar calendar, Widget widget) {
        return new DiaryMainService(context, calendar, widget);
    }

    private void repeatTasks(List<DiaryTask> diaryTasks) {
        diaryTasks.forEach(diaryTask -> {
            diaryTask.getDaysOfAlarm()
                    .stream()
                    .filter(dayOfAlarm -> toDayOfWeek(dayOfAlarm) != Calendar.DAY_OF_WEEK && !diaryTask.isRewriteRepeater())
                    .forEach(dayOfAlarm -> {
                        Calendar calendar = Calendar.getInstance();
                        dayOfAlarm.repeat(toDayOfWeek(dayOfAlarm), calendar, diaryTask, context);
                    });
            DiaryTaskRepo.getInstance(context).updateRewriteRepeater(true, diaryTask.getId());

        });
    }

    private void getWeekTasks() {
        List<TaskOfDay> taskOfDays = new ArrayList<>();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        for (int i = 0; i < 7; i++) {
            List<DiaryTask> diaryTasks = DiaryTaskRepo.getInstance(context)
                    .findAllByWeekAndDate(calendar.get(Calendar.WEEK_OF_YEAR),
                            SimpleDateFormat.getDateInstance().format(calendar.getTimeInMillis()))
                    .orElse(null);
            taskOfDays.add(toTaskOfDay(diaryTasks, calendar));

            if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                repeatTasks(Objects.requireNonNull(diaryTasks));
            }

            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        DiaryMainListAdapter adapter = new DiaryMainListAdapter(context, taskOfDays);
        widget.getExpandableList().getExpandableListView().setAdapter(adapter);
    }
}
