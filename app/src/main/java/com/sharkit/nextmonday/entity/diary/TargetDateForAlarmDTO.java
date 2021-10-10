package com.sharkit.nextmonday.entity.diary;

import com.sharkit.nextmonday.entity.transformer.TransformerDiary;

public class TargetDateForAlarmDTO extends TransformerDiary {
    private boolean repeatMonday;
    private boolean repeatTuesday;
    private boolean repeatWednesday;
    private boolean repeatThursday;
    private boolean repeatFriday;
    private boolean repeatSaturday;
    private boolean repeatSunday;

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

}
