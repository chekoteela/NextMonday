package com.sharkit.nextmonday.Users;

public class BaseTarget {

    private  int r_year, r_day, r_month, r_hour, r_minute, repeat,repeat_monday, repeat_tuesday, repeat_wednesday,
            repeat_thursday, repeat_friday, repeat_saturday, repeat_sunday,complete;
    private String text_target,time;


    public BaseTarget(){}



    public int getR_year() {
        return r_year;
    }
    public void setR_year(int r_year) {
        this.r_year = r_year;
    }
    public int getR_day() {
        return r_day;
    }
    public void setR_day(int r_day) {
        this.r_day = r_day;
    }
    public int getR_month() {
        return r_month;
    }
    public void setR_month(int r_month) {
        this.r_month = r_month;
    }
    public int getR_hour() {
        return r_hour;
    }
    public void setR_hour(int r_hour) {
        this.r_hour = r_hour;
    }
    public int getR_minute() {
        return r_minute;
    }
    public void setR_minute(int r_minute) {
        this.r_minute = r_minute;
    }
    public String getText_target() {
        return text_target;
    }
    public void setText_target(String text_target) {
        this.text_target = text_target;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getRepeat_monday() {
        return repeat_monday;
    }

    public void setRepeat_monday(int repeat_monday) {
        this.repeat_monday = repeat_monday;
    }

    public int getRepeat_tuesday() {
        return repeat_tuesday;
    }

    public void setRepeat_tuesday(int repeat_tuesday) {
        this.repeat_tuesday = repeat_tuesday;
    }

    public int getRepeat_wednesday() {
        return repeat_wednesday;
    }

    public void setRepeat_wednesday(int repeat_wednesday) {
        this.repeat_wednesday = repeat_wednesday;
    }

    public int getRepeat_thursday() {
        return repeat_thursday;
    }

    public void setRepeat_thursday(int repeat_thursday) {
        this.repeat_thursday = repeat_thursday;
    }

    public int getRepeat_friday() {
        return repeat_friday;
    }

    public void setRepeat_friday(int repeat_friday) {
        this.repeat_friday = repeat_friday;
    }

    public int getRepeat_saturday() {
        return repeat_saturday;
    }

    public void setRepeat_saturday(int repeat_saturday) {
        this.repeat_saturday = repeat_saturday;
    }

    public int getRepeat_sunday() {
        return repeat_sunday;
    }

    public void setRepeat_sunday(int repeat_sunday) {
        this.repeat_sunday = repeat_sunday;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getComplete() {
        return complete;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
