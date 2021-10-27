package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;
import com.sharkit.nextmonday.entity.calculator.PFC;

public class TransformerFood {

    public static LinkFoodDTO transformToFoodInfo(FoodInfo foodInfo, LinkFoodDTO linkFoodDTO) {
        linkFoodDTO.setLink(foodInfo.getId());
        return linkFoodDTO;
    }

    public static GeneralDataPFCDTO transformToGeneralDataPFCDTO(FoodInfo foodInfo, GeneralDataPFCDTO generalDataPFCDTO) {
        generalDataPFCDTO.setCalorie(foodInfo.getCalorie());
        generalDataPFCDTO.setCarbohydrate(Float.parseFloat(foodInfo.getCarbohydrate()));
        generalDataPFCDTO.setProtein(Float.parseFloat(foodInfo.getProtein()));
        generalDataPFCDTO.setFat(Float.parseFloat(foodInfo.getFat()));
        generalDataPFCDTO.setName(foodInfo.getName());
        return generalDataPFCDTO;
    }

    public static PFC transformToPFC(FoodInfo foodInfo, PFC pfc) {
        pfc.setProtein(Float.parseFloat(foodInfo.getProtein()));
        pfc.setAggProtein(Float.parseFloat(foodInfo.getAggProtein()));
        pfc.setCaseinProtein(Float.parseFloat(foodInfo.getCaseinProtein()));
        pfc.setSoyProtein(Float.parseFloat(foodInfo.getSoyProtein()));
        pfc.setWheyProtein(Float.parseFloat(foodInfo.getWheyProtein()));
        pfc.setCarbohydrate(Float.parseFloat(foodInfo.getCarbohydrate()));
        pfc.setSimpleCarbohydrate(Float.parseFloat(foodInfo.getSimpleCarbohydrate()));
        pfc.setComplexCarbohydrate(Float.parseFloat(foodInfo.getComplexCarbohydrate()));
        pfc.setFat(Float.parseFloat(foodInfo.getFat()));
        pfc.setSaturatedFat(Float.parseFloat(foodInfo.getSaturatedFat()));
        pfc.setTransFat(Float.parseFloat(foodInfo.getTransFat()));
        pfc.setOmega3(Float.parseFloat(foodInfo.getOmega3()));
        pfc.setOmega6(Float.parseFloat(foodInfo.getOmega6()));
        pfc.setOmega9(Float.parseFloat(foodInfo.getOmega9()));
        pfc.setAla(Float.parseFloat(foodInfo.getAla()));
        pfc.setDha(Float.parseFloat(foodInfo.getDha()));
        pfc.setEpa(Float.parseFloat(foodInfo.getEpa()));
        pfc.setCalcium(Float.parseFloat(foodInfo.getCalcium()));
        pfc.setCellulose(Float.parseFloat(foodInfo.getCellulose()));
        pfc.setWater(Float.parseFloat(foodInfo.getWater()));
        pfc.setSalt(Float.parseFloat(foodInfo.getSalt()));
        pfc.setPotassium(Float.parseFloat(foodInfo.getPotassium()));
        pfc.setCalorie(foodInfo.getCalorie());
        return pfc;
    }
}
