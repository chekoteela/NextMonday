package com.sharkit.nextmonday.entity.diary;

public class ParentItemData {
    private String month;
    private int number;
    private String day;
    private int completeTargets;
    private int allTargets;

    public int getAllTargets() {
        return allTargets;
    }

    public void setAllTargets(int allTargets) {
        this.allTargets = allTargets;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getCompleteTargets() {
        return completeTargets;
    }

    public void setCompleteTargets(int completeTargets) {
        this.completeTargets = completeTargets;
    }
}
