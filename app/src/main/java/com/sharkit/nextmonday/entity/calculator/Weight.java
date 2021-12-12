package com.sharkit.nextmonday.entity.calculator;

public class Weight {
    private float weight;
    private String date;

    public Weight() {
    }

    public Weight(float weight, String date) {
        this.weight = weight;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
