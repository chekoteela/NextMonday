package com.sharkit.nextmonday.entity.calculator;

import java.util.Calendar;

public class LinkFoodDTO {
    private final long id = Calendar.getInstance().getTimeInMillis();
    private String meal;
    private String link;
    private boolean visible;

    public long getId() {
        return id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
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
