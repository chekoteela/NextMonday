package com.sharkit.nextmonday.entity.diary;

import com.sharkit.nextmonday.entity.transformer.TransformerDiary;

public class TargetDiary extends TransformerDiary {
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

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public long getTimeForAlarm() {
        return timeForAlarm;
    }

    public void setTimeForAlarm(long timeForAlarm) {
        this.timeForAlarm = timeForAlarm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRepeatMonday() {
        return repeatMonday;
    }

    public void setRepeatMonday(boolean repeatMonday) {
        this.repeatMonday = repeatMonday;
    }

    public boolean isRepeatTuesday() {
        return repeatTuesday;
    }

    public void setRepeatTuesday(boolean repeatTuesday) {
        this.repeatTuesday = repeatTuesday;
    }

    public boolean isRepeatWednesday() {
        return repeatWednesday;
    }

    public void setRepeatWednesday(boolean repeatWednesday) {
        this.repeatWednesday = repeatWednesday;
    }

    public boolean isRepeatThursday() {
        return repeatThursday;
    }

    public void setRepeatThursday(boolean repeatThursday) {
        this.repeatThursday = repeatThursday;
    }

    public boolean isRepeatFriday() {
        return repeatFriday;
    }

    public void setRepeatFriday(boolean repeatFriday) {
        this.repeatFriday = repeatFriday;
    }

    public boolean isRepeatSaturday() {
        return repeatSaturday;
    }

    public void setRepeatSaturday(boolean repeatSaturday) {
        this.repeatSaturday = repeatSaturday;
    }

    public boolean isRepeatSunday() {
        return repeatSunday;
    }

    public void setRepeatSunday(boolean repeatSunday) {
        this.repeatSunday = repeatSunday;
    }

    public TargetDiary transform(TargetDiaryDTO targetDiaryDTO){
        return transformer(targetDiaryDTO);
    }
    public TargetDateForAlarmDTO transform(TargetDateForAlarmDTO alarmDTO){
        return transformToAlarmDTO(this, alarmDTO);
    }
}
