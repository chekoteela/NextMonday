package com.sharkit.nextmonday.entity.calculator;

import com.sharkit.nextmonday.entity.transformer.TransformerFood;

public class Meal {
    private String name;
    public Meal(){}

    public Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MealTodayDTO transform (MealTodayDTO mealTodayDTO){
        return TransformerFood.transformToMealTodayDTO(this, mealTodayDTO);
    }
}
