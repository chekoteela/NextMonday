package com.sharkit.nextmonday.entity.calculator;

import com.sharkit.nextmonday.entity.transformer.TransformerFood;

import java.io.Serializable;

public class FoodInfo implements Serializable {
    private String name, id;
    private float portion;
    private int calorie;

    private String protein, wheyProtein, soyProtein, aggProtein, caseinProtein;
    private String carbohydrate, simpleCarbohydrate, complexCarbohydrate;
    private String fat, saturatedFat, transFat, omega3, omega6, omega9, dha, ala, epa;
    private String cellulose, water, salt, calcium, potassium;

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

    public float getPortion() {
        return portion;
    }

    public void setPortion(float portion) {
        this.portion = portion;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getWheyProtein() {
        return wheyProtein;
    }

    public void setWheyProtein(String wheyProtein) {
        this.wheyProtein = wheyProtein;
    }

    public String getSoyProtein() {
        return soyProtein;
    }

    public void setSoyProtein(String soyProtein) {
        this.soyProtein = soyProtein;
    }

    public String getAggProtein() {
        return aggProtein;
    }

    public void setAggProtein(String aggProtein) {
        this.aggProtein = aggProtein;
    }

    public String getCaseinProtein() {
        return caseinProtein;
    }

    public void setCaseinProtein(String caseinProtein) {
        this.caseinProtein = caseinProtein;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getSimpleCarbohydrate() {
        return simpleCarbohydrate;
    }

    public void setSimpleCarbohydrate(String simpleCarbohydrate) {
        this.simpleCarbohydrate = simpleCarbohydrate;
    }

    public String getComplexCarbohydrate() {
        return complexCarbohydrate;
    }

    public void setComplexCarbohydrate(String complexCarbohydrate) {
        this.complexCarbohydrate = complexCarbohydrate;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public String getTransFat() {
        return transFat;
    }

    public void setTransFat(String transFat) {
        this.transFat = transFat;
    }

    public String getOmega3() {
        return omega3;
    }

    public void setOmega3(String omega3) {
        this.omega3 = omega3;
    }

    public String getOmega6() {
        return omega6;
    }

    public void setOmega6(String omega6) {
        this.omega6 = omega6;
    }

    public String getOmega9() {
        return omega9;
    }

    public void setOmega9(String omega9) {
        this.omega9 = omega9;
    }

    public String getDha() {
        return dha;
    }

    public void setDha(String dha) {
        this.dha = dha;
    }

    public String getAla() {
        return ala;
    }

    public void setAla(String ala) {
        this.ala = ala;
    }

    public String getEpa() {
        return epa;
    }

    public void setEpa(String epa) {
        this.epa = epa;
    }

    public String getCellulose() {
        return cellulose;
    }

    public void setCellulose(String cellulose) {
        this.cellulose = cellulose;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCalcium() {
        return calcium;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public String getPotassium() {
        return potassium;
    }

    public void setPotassium(String potassium) {
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
