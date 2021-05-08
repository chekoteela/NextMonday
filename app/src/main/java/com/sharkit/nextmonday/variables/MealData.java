package com.sharkit.nextmonday.variables;

public class MealData {
    private String code,  meal, date;
    float number;
    private long date_millis;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDate_millis() {
        return date_millis;
    }

    public void setDate_millis(long date_millis) {
        this.date_millis = date_millis;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }


    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}
