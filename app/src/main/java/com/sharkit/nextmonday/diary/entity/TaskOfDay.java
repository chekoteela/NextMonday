package com.sharkit.nextmonday.diary.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TaskOfDay {

    private List<DiaryTask> diaryTasks;
    private int dayNumber;
    private int dayName;
    private int month;
}
