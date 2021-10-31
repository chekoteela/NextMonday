package com.sharkit.nextmonday.service.calculator.ration_service;

import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;

import java.util.ArrayList;

public class NutritionService {
    private final ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS;

    public NutritionService(ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS){
        this.generalDataPFCDTOS = generalDataPFCDTOS;
    }

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
}
