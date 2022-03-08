package com.sharkit.nextmonday.diary.transformer;

import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.service.DiaryCreateTaskService;

public class DiaryTransformer {

    public static DiaryTask toDiaryTask(DiaryCreateTaskService service, EditTextWidget widget){
        return DiaryTask.builder()
                .daysOfAlarm(service.getDaysOfAlarm())
                .hour(service.getHour())
                .minute(service.getMinute())
                .nameOfTask(widget.getNameOfTask().getText().toString())
                .description(widget.getDescription().getText().toString())
                .build();
    }

}
