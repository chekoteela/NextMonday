package com.sharkit.nextmonday.db.sqlite.calculator.product;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.sharkit.nextmonday.entity.calculator.FoodInfo;

public class ProductPFCDataService {

    private final Context context;
    private final ProductPFCData productPFCData;

    public ProductPFCDataService(Context context) {
        productPFCData = new ProductPFCData(context);
        productPFCData.onCreate(productPFCData.getReadableDatabase());
        this.context = context;
    }

    public void create(FoodInfo foodInfo){
        try {
            productPFCData.create(foodInfo);
        }catch (SQLiteConstraintException ignored){}
    }

    public FoodInfo findByID(String id) {
       return productPFCData.findById(id);
    }
}
