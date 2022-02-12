package com.sharkit.nextmonday.entity.diary;

import lombok.Data;

@Data
public class TargetDiaryDTO {
    private String id;
    private String text;
    private String description;
    private String date;
    private boolean alarm;
    private boolean status;
    private boolean repeatMonday;
    private boolean repeatTuesday;
    private boolean repeatWednesday;
    private boolean repeatThursday;
    private boolean repeatFriday;
    private boolean repeatSaturday;
    private boolean repeatSunday;
    private long timeForAlarm;
    private boolean visible;

}
