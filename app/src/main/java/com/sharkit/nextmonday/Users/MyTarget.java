package com.sharkit.nextmonday.Users;

public class MyTarget {
    private String date, name, description, repeat;
    private boolean status, repeat_monday, repeat_tuesday, repeat_wednesday,
            repeat_thursday, repeat_friday, repeat_saturday, repeat_sunday;
    private Long time_alarm;

    public Long getTime_alarm() {
        return time_alarm;
    }

    public void setTime_alarm(Long time_alarm) {
        this.time_alarm = time_alarm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRepeat_monday() {
        return repeat_monday;
    }

    public void setRepeat_monday(boolean repeat_monday) {
        this.repeat_monday = repeat_monday;
    }

    public boolean isRepeat_tuesday() {
        return repeat_tuesday;
    }

    public void setRepeat_tuesday(boolean repeat_tuesday) {
        this.repeat_tuesday = repeat_tuesday;
    }

    public boolean isRepeat_wednesday() {
        return repeat_wednesday;
    }

    public void setRepeat_wednesday(boolean repeat_wednesday) {
        this.repeat_wednesday = repeat_wednesday;
    }

    public boolean isRepeat_thursday() {
        return repeat_thursday;
    }

    public void setRepeat_thursday(boolean repeat_thursday) {
        this.repeat_thursday = repeat_thursday;
    }

    public boolean isRepeat_friday() {
        return repeat_friday;
    }

    public void setRepeat_friday(boolean repeat_friday) {
        this.repeat_friday = repeat_friday;
    }

    public boolean isRepeat_saturday() {
        return repeat_saturday;
    }

    public void setRepeat_saturday(boolean repeat_saturday) {
        this.repeat_saturday = repeat_saturday;
    }

    public boolean isRepeat_sunday() {
        return repeat_sunday;
    }

    public void setRepeat_sunday(boolean repeat_sunday) {
        this.repeat_sunday = repeat_sunday;
    }
}
