package com.sharkit.nextmonday.entity.calculator;

import java.util.Calendar;

import lombok.Data;

@Data
public class LinkFoodDTO {
    private final long id = Calendar.getInstance().getTimeInMillis();
    private String link, meal;
    private boolean visible;
    private int portion;


}
