package com.sharkit.nextmonday.entity.diary;

public class TargetDiaryDTO {
    private String id;
    private String text;
    private String description;
    private String date;
    private boolean status;
    private boolean repeatMonday;
    private boolean repeatTuesday;
    private boolean repeatWednesday;
    private boolean repeatThursday;
    private boolean repeatFriday;
    private boolean repeatSaturday;
    private boolean repeatSunday;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isRepeatMonday() {
        return repeatMonday;
    }

    public boolean isRepeatTuesday() {
        return repeatTuesday;
    }

    public boolean isRepeatWednesday() {
        return repeatWednesday;
    }

    public boolean isRepeatThursday() {
        return repeatThursday;
    }

    public boolean isRepeatFriday() {
        return repeatFriday;
    }

    public boolean isRepeatSaturday() {
        return repeatSaturday;
    }

    public boolean isRepeatSunday() {
        return repeatSunday;
    }
}
