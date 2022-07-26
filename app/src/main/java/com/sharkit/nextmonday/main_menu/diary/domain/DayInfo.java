package com.sharkit.nextmonday.main_menu.diary.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DayInfo {
    private Integer dayNumber;
    private String dayOfWeek;
    private String month;
    private Long dataTime;
    private List<DiaryTask> diaryTasks;
}
