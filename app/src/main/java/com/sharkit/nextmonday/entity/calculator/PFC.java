package com.sharkit.nextmonday.entity.calculator;

import lombok.Data;

@Data
public class PFC {
    private int calorie;
    private float protein, wheyProtein, soyProtein, aggProtein, caseinProtein;
    private float carbohydrate, simpleCarbohydrate, complexCarbohydrate;
    private float fat, saturatedFat, transFat, omega3, omega6, omega9, dha, ala, epa;
    private float cellulose, water, salt, calcium, potassium;

}
