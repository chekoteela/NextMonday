package com.sharkit.nextmonday.entity.calculator;

import java.util.Calendar;

public class LinkFoodDTO {
    private final long id = Calendar.getInstance().getTimeInMillis();
    private String link, meal;
    private boolean visible;
    private int portion;

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
