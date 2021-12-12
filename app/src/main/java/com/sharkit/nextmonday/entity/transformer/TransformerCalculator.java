package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.calculator.Weight;
import com.sharkit.nextmonday.entity.calculator.WeightDTO;

public class TransformerCalculator {
    public static WeightDTO transform(Weight weight){
        WeightDTO weightDTO = new WeightDTO();
        weightDTO.setWeight(weight.getWeight());
        weightDTO.setDate(weight.getDate());
        return weightDTO;
    }
}
