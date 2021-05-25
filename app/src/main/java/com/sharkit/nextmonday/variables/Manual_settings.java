package com.sharkit.nextmonday.variables;

public class Manual_settings {
    private String var;
    private float weight, watter, desired_weight;
    private int protein, carbohydrate, fat;

    public float getDesired_weight() {
        return desired_weight;
    }

    public void setDesired_weight(float desired_weight) {
        this.desired_weight = desired_weight;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWatter() {
        return watter;
    }

    public void setWatter(float watter) {
        this.watter = watter;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
}
