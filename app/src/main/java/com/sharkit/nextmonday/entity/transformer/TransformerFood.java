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
        generalDataPFCDTO.setCarbohydrate(foodInfo.getCarbohydrate());
        generalDataPFCDTO.setProtein(foodInfo.getProtein());
        generalDataPFCDTO.setFat(foodInfo.getFat());
        generalDataPFCDTO.setName(foodInfo.getName());
        generalDataPFCDTO.setPortion(foodInfo.getPortion());
        return generalDataPFCDTO;
    }

    public static PFC transformToPFC(FoodInfo foodInfo, PFC pfc) {
        pfc.setProtein(foodInfo.getProtein());
        pfc.setAggProtein(foodInfo.getAggProtein());
        pfc.setCaseinProtein(foodInfo.getCaseinProtein());
        pfc.setSoyProtein(foodInfo.getSoyProtein());
        pfc.setWheyProtein(foodInfo.getWheyProtein());
        pfc.setCarbohydrate(foodInfo.getCarbohydrate());
        pfc.setSimpleCarbohydrate(foodInfo.getSimpleCarbohydrate());
        pfc.setComplexCarbohydrate(foodInfo.getComplexCarbohydrate());
        pfc.setFat(foodInfo.getFat());
        pfc.setSaturatedFat(foodInfo.getSaturatedFat());
        pfc.setTransFat(foodInfo.getTransFat());
        pfc.setOmega3(foodInfo.getOmega3());
        pfc.setOmega6(foodInfo.getOmega6());
        pfc.setOmega9(foodInfo.getOmega9());
        pfc.setAla(foodInfo.getAla());
        pfc.setDha(foodInfo.getDha());
        pfc.setEpa(foodInfo.getEpa());
        pfc.setCalcium(foodInfo.getCalcium());
        pfc.setCellulose(foodInfo.getCellulose());
        pfc.setWater(foodInfo.getWater());
        pfc.setSalt(foodInfo.getSalt());
        pfc.setPotassium(foodInfo.getPotassium());
        pfc.setCalorie(foodInfo.getCalorie());
        return pfc;
    }
}
