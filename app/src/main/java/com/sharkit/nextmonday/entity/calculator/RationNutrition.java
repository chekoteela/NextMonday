package com.sharkit.nextmonday.entity.calculator;

import java.util.ArrayList;

public class RationNutrition {
    private String nameMeal;
    private ArrayList<Float> carbohydrate, protein, fat;
    private ArrayList<Integer> calorie;

    public float getAllCarbohydrate() {
        return getAllNutritionFloat(carbohydrate);
    }

    public float getAllProtein() {
        return getAllNutritionFloat(protein);
    }

    public float getAllFat() {
        return getAllNutritionFloat(fat);
    }

    public float getAllCalorie() {
        return getAllNutritionInt(calorie);
    }

    public void setCarbohydrate(ArrayList<Float> carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setProtein(ArrayList<Float> protein) {
        this.protein = protein;
    }

    public void setFat(ArrayList<Float> fat) {
        this.fat = fat;
    }

    public void setCalorie(ArrayList<Integer> calorie) {
        this.calorie = calorie;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public void setNameMeal(String nameMeal) {
        this.nameMeal = nameMeal;
    }

    private float getAllNutritionFloat(ArrayList<Float> values){
        float sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }
        return sum;
    }
    private int getAllNutritionInt(ArrayList<Integer> values){
        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }
        return sum;
    }
}
