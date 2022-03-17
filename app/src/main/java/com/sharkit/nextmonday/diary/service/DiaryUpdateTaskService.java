package com.sharkit.nextmonday.diary.service;

import static com.sharkit.nextmonday.diary.transformer.DateTransformer.toDayName;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryUpdateTaskService {

    private final Widget widget;
    private final Context context;

    private static final String EMPTY_TIME_FORMAT = "--:--";

    public DiaryUpdateTaskService(Widget widget, Context context) {
        this.widget = widget;
        this.context = context;
    }

    @SuppressLint("NewApi")
    public void writeFields(DiaryTask diaryTask) {
        List<String> days = new ArrayList<>();
        diaryTask.getDaysOfAlarm()
                .forEach(dayOfAlarm -> days.add(toDayName(context, dayOfAlarm)));

        if (diaryTask.getHour() == -1 || diaryTask.getMinute() == -1){
            widget.getTextView().getRangTime().setText(EMPTY_TIME_FORMAT);
        }else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, diaryTask.getHour());
            calendar.set(Calendar.MINUTE, diaryTask.getMinute());
            widget.getTextView().getRangTime().setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTimeInMillis()));
            widget.getSwitch().getTakeTime().setChecked(true);
        }
        widget.getSwitch().getRepeat().setChecked(diaryTask.getDaysOfAlarm().size() > 0);
        widget.getTextField().getNameOfTask().setText(diaryTask.getNameOfTask());
        widget.getTextView().getRepeatDays().setText(String.join(", ", days));
        widget.getTextField().getDescription().setText(diaryTask.getDescription());
    }


}
