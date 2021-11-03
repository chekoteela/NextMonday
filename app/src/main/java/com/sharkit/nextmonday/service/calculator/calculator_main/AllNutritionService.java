package com.sharkit.nextmonday.service.calculator.calculator_main;

import com.sharkit.nextmonday.entity.calculator.PFC;

import java.util.ArrayList;

public class AllNutritionService {
    private ArrayList<PFC> pfc;
    private int calorie;

    private float protein, wheyProtein, soyProtein, aggProtein, caseinProtein;
    private float carbohydrate, simpleCarbohydrate, complexCarbohydrate;
    private float fat, saturatedFat, transFat, omega3, omega6, omega9, dha, ala, epa;
    private float cellulose, water, salt, calcium, potassium;

    public AllNutritionService() {
    }

    public AllNutritionService(ArrayList<PFC> pfc) {
        this.pfc = pfc;
    }

    public PFC getAll(ArrayList<PFC> pfc) {
        this.pfc = pfc;
        PFC product = new PFC();
        product.setCalorie(getCalorie());
        product.setWheyProtein(getWheyProtein());
        product.setSoyProtein(getSoyProtein());
        product.setAggProtein(getAggProtein());
        product.setCaseinProtein(getCaseinProtein());
        product.setCarbohydrate(getCarbohydrate());
        product.setSimpleCarbohydrate(getSimpleCarbohydrate());
        product.setComplexCarbohydrate(getComplexCarbohydrate());
        product.setFat(getFat());
        product.setSaturatedFat(getSaturatedFat());
        product.setTransFat(getTransFat());
        product.setOmega3(getOmega3());
        product.setOmega6(getOmega6());
        product.setOmega9(getOmega9());
        product.setDha(getDha());
        product.setEpa(getEpa());
        product.setAla(getAla());
        product.setCellulose(getCellulose());
        product.setWater(getWater());
        product.setSalt(getSalt());
        product.setCalcium(getCalcium());
        product.setPotassium(getPotassium());
        product.setProtein(getProtein());
        return product;
    }

    public float getProtein() {
        for (int i = 0; i < pfc.size(); i++) {
            protein += pfc.get(i).getProtein();
        }
        return protein;
    }

    public int getCalorie() {
        for (int i = 0; i < pfc.size(); i++) {
            calorie += pfc.get(i).getCalorie();
        }
        return calorie;
    }

    public float getWheyProtein() {
        for (int i = 0; i < pfc.size(); i++) {
            wheyProtein += pfc.get(i).getWheyProtein();
        }
        return wheyProtein;
    }

    public float getSoyProtein() {
        for (int i = 0; i < pfc.size(); i++) {
            soyProtein += pfc.get(i).getSoyProtein();
        }
        return soyProtein;
    }

    public float getAggProtein() {
        for (int i = 0; i < pfc.size(); i++) {
            aggProtein += pfc.get(i).getAggProtein();
        }
        return aggProtein;
    }

    public float getCaseinProtein() {
        for (int i = 0; i < pfc.size(); i++) {
            caseinProtein += pfc.get(i).getCaseinProtein();
        }
        return caseinProtein;
    }

    public float getCarbohydrate() {
        for (int i = 0; i < pfc.size(); i++) {
            carbohydrate += pfc.get(i).getCarbohydrate();
        }
        return carbohydrate;
    }

    public float getSimpleCarbohydrate() {
        for (int i = 0; i < pfc.size(); i++) {
            simpleCarbohydrate += pfc.get(i).getSimpleCarbohydrate();
        }
        return simpleCarbohydrate;
    }

    public float getComplexCarbohydrate() {
        for (int i = 0; i < pfc.size(); i++) {
            complexCarbohydrate += pfc.get(i).getComplexCarbohydrate();
        }
        return complexCarbohydrate;
    }

    public float getFat() {
        for (int i = 0; i < pfc.size(); i++) {
            fat += pfc.get(i).getFat();
        }
        return fat;
    }

    public float getSaturatedFat() {
        for (int i = 0; i < pfc.size(); i++) {
            saturatedFat += pfc.get(i).getSaturatedFat();
        }
        return saturatedFat;
    }

    public float getTransFat() {
        for (int i = 0; i < pfc.size(); i++) {
            transFat += pfc.get(i).getTransFat();
        }
        return transFat;
    }

    public float getOmega3() {
        for (int i = 0; i < pfc.size(); i++) {
            omega3 += pfc.get(i).getOmega3();
        }
        return omega3;
    }

    public float getOmega6() {
        for (int i = 0; i < pfc.size(); i++) {
            omega6 += pfc.get(i).getOmega6();
        }
        return omega6;
    }

    public float getOmega9() {
        for (int i = 0; i < pfc.size(); i++) {
            omega9 += pfc.get(i).getOmega9();
        }
        return omega9;
    }

    public float getDha() {
        for (int i = 0; i < pfc.size(); i++) {
            dha += pfc.get(i).getDha();
        }
        return dha;
    }

    public float getAla() {
        for (int i = 0; i < pfc.size(); i++) {
            ala += pfc.get(i).getAla();
        }
        return ala;
    }

    public float getEpa() {
        for (int i = 0; i < pfc.size(); i++) {
            epa += pfc.get(i).getEpa();
        }
        return epa;
    }

    public float getCellulose() {
        for (int i = 0; i < pfc.size(); i++) {
            cellulose += pfc.get(i).getCellulose();
        }
        return cellulose;
    }

    public float getWater() {
        for (int i = 0; i < pfc.size(); i++) {
            water += pfc.get(i).getWater();
        }
        return water;
    }

    public float getSalt() {
        for (int i = 0; i < pfc.size(); i++) {
            salt += pfc.get(i).getSalt();
        }
        return salt;
    }

    public float getCalcium() {
        for (int i = 0; i < pfc.size(); i++) {
            calcium += pfc.get(i).getCalcium();
        }
        return calcium;
    }

    public float getPotassium() {
        for (int i = 0; i < pfc.size(); i++) {
            potassium += pfc.get(i).getPotassium();
        }
        return potassium;
    }
}