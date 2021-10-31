package com.sharkit.nextmonday.entity.calculator;

import com.sharkit.nextmonday.entity.transformer.TransformerFood;

import java.io.Serializable;

public class FoodInfo implements Serializable {
    private String name, id;
    private int portion;
    private int calorie;

    private float protein, wheyProtein, soyProtein, aggProtein, caseinProtein;
    private float carbohydrate, simpleCarbohydrate, complexCarbohydrate;
    private float fat, saturatedFat, transFat, omega3, omega6, omega9, dha, ala, epa;
    private float cellulose, water, salt, calcium, potassium;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getWheyProtein() {
        return wheyProtein;
    }

    public void setWheyProtein(float wheyProtein) {
        this.wheyProtein = wheyProtein;
    }

    public float getSoyProtein() {
        return soyProtein;
    }

    public void setSoyProtein(float soyProtein) {
        this.soyProtein = soyProtein;
    }

    public float getAggProtein() {
        return aggProtein;
    }

    public void setAggProtein(float aggProtein) {
        this.aggProtein = aggProtein;
    }

    public float getCaseinProtein() {
        return caseinProtein;
    }

    public void setCaseinProtein(float caseinProtein) {
        this.caseinProtein = caseinProtein;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getSimpleCarbohydrate() {
        return simpleCarbohydrate;
    }

    public void setSimpleCarbohydrate(float simpleCarbohydrate) {
        this.simpleCarbohydrate = simpleCarbohydrate;
    }

    public float getComplexCarbohydrate() {
        return complexCarbohydrate;
    }

    public void setComplexCarbohydrate(float complexCarbohydrate) {
        this.complexCarbohydrate = complexCarbohydrate;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public float getTransFat() {
        return transFat;
    }

    public void setTransFat(float transFat) {
        this.transFat = transFat;
    }

    public float getOmega3() {
        return omega3;
    }

    public void setOmega3(float omega3) {
        this.omega3 = omega3;
    }

    public float getOmega6() {
        return omega6;
    }

    public void setOmega6(float omega6) {
        this.omega6 = omega6;
    }

    public float getOmega9() {
        return omega9;
    }

    public void setOmega9(float omega9) {
        this.omega9 = omega9;
    }

    public float getDha() {
        return dha;
    }

    public void setDha(float dha) {
        this.dha = dha;
    }

    public float getAla() {
        return ala;
    }

    public void setAla(float ala) {
        this.ala = ala;
    }

    public float getEpa() {
        return epa;
    }

    public void setEpa(float epa) {
        this.epa = epa;
    }

    public float getCellulose() {
        return cellulose;
    }

    public void setCellulose(float cellulose) {
        this.cellulose = cellulose;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getSalt() {
        return salt;
    }

    public void setSalt(float salt) {
        this.salt = salt;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public LinkFoodDTO transform(LinkFoodDTO linkFoodDTO){
        return TransformerFood.transformToFoodInfo(this, linkFoodDTO);
    }
    public GeneralDataPFCDTO transform (GeneralDataPFCDTO generalDataPFCDTO){
        return TransformerFood.transformToGeneralDataPFCDTO(this, generalDataPFCDTO);
    }
    public PFC transform(PFC pfc){
        return TransformerFood.transformToPFC(this, pfc);
    }

}
