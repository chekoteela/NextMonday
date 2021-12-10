package com.sharkit.nextmonday.service.calculator.ration_service;

import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;

import java.util.ArrayList;

public class NutritionService {
    private ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS;

    public NutritionService(ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS){
        this.generalDataPFCDTOS = generalDataPFCDTOS;
    }
    public NutritionService(){}

    public ArrayList<Integer> getCalorie() {
        ArrayList<Integer> calorie = new ArrayList<>();
        for (int i = 0; i < generalDataPFCDTOS.size(); i++) {
            calorie.add(generalDataPFCDTOS.get(i).getCalorie());
        }
        return calorie;
    }
    public ArrayList<Float> getProtein(){
        ArrayList<Float> protein = new ArrayList<>();
        for (int i = 0; i < generalDataPFCDTOS.size(); i++) {
            protein.add(generalDataPFCDTOS.get(i).getProtein());
        }

        return protein;
    }
    public ArrayList<Float> getFat(){
        ArrayList<Float> fat = new ArrayList<>();
        for (int i = 0; i < generalDataPFCDTOS.size(); i++) {
            fat.add(generalDataPFCDTOS.get(i).getFat());
        }
        return fat;
    }
    public ArrayList<Float> getCarbohydrate(){
        ArrayList<Float> carbohydrate = new ArrayList<>();
        for (int i = 0; i < generalDataPFCDTOS.size(); i++) {
            carbohydrate.add(generalDataPFCDTOS.get(i).getCarbohydrate());
        }
        return carbohydrate;
    }

    public int getCalorie(GeneralDataPFCDTO generalDataPFCDTO, float portion) {
        return (int) (generalDataPFCDTO.getCalorie() / generalDataPFCDTO.getPortion() * portion);
    }
    public float getCarbohydrate(GeneralDataPFCDTO generalDataPFCDTO, float portion) {
        return generalDataPFCDTO.getCarbohydrate() / generalDataPFCDTO.getPortion() * portion;
    }
    public float getFat(GeneralDataPFCDTO generalDataPFCDTO, float portion) {
        return generalDataPFCDTO.getFat() / generalDataPFCDTO.getPortion() * portion;
    }
    public float getProtein(GeneralDataPFCDTO generalDataPFCDTO, float portion) {
        return generalDataPFCDTO.getProtein() / generalDataPFCDTO.getPortion() * portion;
    }
}
