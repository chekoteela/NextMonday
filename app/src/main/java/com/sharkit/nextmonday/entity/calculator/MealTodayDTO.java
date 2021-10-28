package com.sharkit.nextmonday.entity.calculator;

import java.util.ArrayList;

public class MealTodayDTO {
    private String name;
    private ArrayList<GeneralDataPFCDTO> pfcDTOs;

    public ArrayList<GeneralDataPFCDTO> getPfcDTOs() {
        return pfcDTOs;
    }

    public void setPfcDTOs(ArrayList<GeneralDataPFCDTO> pfcDTOs) {
        this.pfcDTOs = pfcDTOs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
