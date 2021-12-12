package com.sharkit.nextmonday.db.sqlite.calculator.weight;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sharkit.nextmonday.entity.calculator.Weight;
import com.sharkit.nextmonday.entity.calculator.WeightDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class WeightDataService extends WeightData{
    private final WeightData weightData;

    public WeightDataService(Context context) {
        super(context);
        weightData = new WeightData(context);
        onCreate(new WeightData(context).getReadableDatabase());
    }

    public void create(Weight weight){
        if (weightData.exist(new SimpleDateFormat(SHOW_DATE_FORMAT).format(weight.getDate()))) {
            weightData.update(weight);
        }else {
            weightData.create(weight);
        }
    }

    public ArrayList<WeightDTO> getAll() {
        return weightData.getAllWeightDTO();
    }

    public float getWeight() {
        return weightData.getWeight();
    }
}
