package com.sharkit.nextmonday.entity.calculator;

import lombok.Data;

@Data
public class GeneralDataPFCDTO {
    private String name, id;
    private int portion, calorie;
    private float protein, carbohydrate, fat;
}
