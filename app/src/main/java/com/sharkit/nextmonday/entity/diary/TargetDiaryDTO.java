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
    private long timeForAlarm;

    public long getTimeForAlarm() {
        return timeForAlarm;
    }

    public void setTimeForAlarm(long timeForAlarm) {
        this.timeForAlarm = timeForAlarm;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setRepeatMonday(boolean repeatMonday) {
        this.repeatMonday = repeatMonday;
    }

    public void setRepeatTuesday(boolean repeatTuesday) {
        this.repeatTuesday = repeatTuesday;
    }

    public void setRepeatWednesday(boolean repeatWednesday) {
        this.repeatWednesday = repeatWednesday;
    }

    public void setRepeatThursday(boolean repeatThursday) {
        this.repeatThursday = repeatThursday;
    }

    public void setRepeatFriday(boolean repeatFriday) {
        this.repeatFriday = repeatFriday;
    }

    public void setRepeatSaturday(boolean repeatSaturday) {
        this.repeatSaturday = repeatSaturday;
    }

    public void setRepeatSunday(boolean repeatSunday) {
        this.repeatSunday = repeatSunday;
    }

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
