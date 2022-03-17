package com.sharkit.nextmonday.diary.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskOfDay {

    private List<DiaryTask> diaryTasks;
    private String date;
    private int dayNumber;
    private int dayName;
    private int month;
}
