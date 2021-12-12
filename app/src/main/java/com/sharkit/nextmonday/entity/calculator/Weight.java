package com.sharkit.nextmonday.entity.calculator;

public class Weight {
    private float weight;
    private long date;

    public Weight() {
    }

    public Weight(float weight, long date) {
        this.weight = weight;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
