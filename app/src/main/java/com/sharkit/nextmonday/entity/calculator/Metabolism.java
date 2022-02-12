package com.sharkit.nextmonday.entity.calculator;

import lombok.Data;

@Data
public class Metabolism {
    private float currentWeight, desireWeight, height, activity, target;
    private String sex;
    private int calorie;
}


