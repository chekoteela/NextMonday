package com.sharkit.nextmonday.entity.calculator;

import com.sharkit.nextmonday.entity.transformer.TransformerFood;

import java.io.Serializable;

import lombok.Data;

@Data
public class FoodInfo implements Serializable {
    private String name, id;
    private int portion;
    private int calorie;

    private float protein, wheyProtein, soyProtein, aggProtein, caseinProtein;
    private float carbohydrate, simpleCarbohydrate, complexCarbohydrate;
    private float fat, saturatedFat, transFat, omega3, omega6, omega9, dha, ala, epa;
    private float cellulose, water, salt, calcium, potassium;

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
