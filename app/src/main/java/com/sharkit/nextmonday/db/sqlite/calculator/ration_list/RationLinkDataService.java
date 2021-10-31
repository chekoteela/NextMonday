package com.sharkit.nextmonday.db.sqlite.calculator.ration_list;

import android.content.Context;

import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;

import java.util.ArrayList;

public class RationLinkDataService {
    private final Context context;
    private final RationLinkData rationLinkData;

    public RationLinkDataService(Context context) {
        rationLinkData = new RationLinkData(context);
        rationLinkData.onCreate(rationLinkData.getReadableDatabase());
        this.context = context;
    }

    public void create(LinkFoodDTO linkFoodDTO){
        rationLinkData.create(linkFoodDTO);
    }

    public ArrayList<String> findByMealAndDate(String meal, String date) {
      return rationLinkData.findByMealAndDate(meal, date);
    }
}
