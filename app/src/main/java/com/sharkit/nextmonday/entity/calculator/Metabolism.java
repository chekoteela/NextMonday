package com.sharkit.nextmonday.entity.calculator;

import com.sharkit.nextmonday.entity.transformer.TransformerFood;

public class Metabolism {
    private float currentWeight, desireWeight, height, activity, target;
    private String sex;
    private int calorie;

    public float getCurrentWeight() {
        return currentWeight;
    }

    public float getActivity() {
        return activity;
    }

    public void setActivity(float activity) {
        this.activity = activity;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public float getDesireWeight() {
        return desireWeight;
    }

    public void setDesireWeight(float desireWeight) {
        this.desireWeight = desireWeight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

}


