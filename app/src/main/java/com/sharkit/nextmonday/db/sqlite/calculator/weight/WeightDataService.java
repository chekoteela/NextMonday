package com.sharkit.nextmonday.db.sqlite.calculator.weight;

import android.content.Context;

import com.sharkit.nextmonday.entity.calculator.Weight;

public class WeightDataService extends WeightData{
    private WeightData weightData;

    public WeightDataService(Context context) {
        super(context);
        weightData = new WeightData(context);
        onCreate(new WeightData(context).getReadableDatabase());
    }

    public void create(Weight weight){
        weightData.create(weight);
    }

}
