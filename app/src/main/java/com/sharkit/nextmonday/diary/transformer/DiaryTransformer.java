package com.sharkit.nextmonday.diary.transformer;

import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.entity.TaskOfDay;
import com.sharkit.nextmonday.diary.service.DiaryCreateTaskService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DiaryTransformer {

    public static DiaryTask toDiaryTask(DiaryCreateTaskService service, EditTextWidget widget, String date){
        return DiaryTask.builder()
                .daysOfAlarm(service.getDaysOfAlarm())
                .date(date)
                .hour(service.getHour())
                .minute(service.getMinute())
                .nameOfTask(widget.getNameOfTask().getText().toString())
                .description(widget.getDescription().getText().toString())
                .build();
    }

    public static TaskOfDay toTaskOfDay(List<DiaryTask> diaryTasks, Calendar calendar){
        return TaskOfDay.builder()
                .date(SimpleDateFormat.getDateInstance().format(calendar.getTimeInMillis()))
                .dayNumber(calendar.get(Calendar.DATE))
                .dayName(calendar.get(Calendar.DAY_OF_WEEK))
                .month(calendar.get(Calendar.MONTH))
                .diaryTasks(diaryTasks)
                .build();
    }

}
