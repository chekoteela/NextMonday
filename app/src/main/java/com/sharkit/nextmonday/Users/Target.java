package com.sharkit.nextmonday.Users;

public class Target {
    private static int year, day, month;
    public static String  moderator;
    private static Long timeForChange;

    public static Long getTimeForChange() {
        return timeForChange;
    }

    public static void setTimeForChange(Long timeForChange) {
        Target.timeForChange = timeForChange;
    }

    public static int getYear() {
        return year;
    }

    public static void setYear(int year) {
        Target.year = year;
    }

    public static int getDay() {
        return day;
    }

    public static void setDay(int day) {
        Target.day = day;
    }

    public static int getMonth() {
        return month;
    }

    public static void setMonth(int month) {
        Target.month = month;
    }

    public static String getModerator() {
        return moderator;
    }

    public static void setModerator(String moderator) {
        Target.moderator = moderator;
    }
}
