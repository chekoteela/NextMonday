package com.sharkit.nextmonday.entity.calculator;

import java.util.Calendar;

public class LinkFoodDTO {
    private final long id = Calendar.getInstance().getTimeInMillis();
    private String name;
    private String link;
    private boolean visible;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
