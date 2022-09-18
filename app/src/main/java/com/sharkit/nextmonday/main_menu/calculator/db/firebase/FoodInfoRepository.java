package com.sharkit.nextmonday.main_menu.calculator.db.firebase;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;

public class FoodInfoRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String path;

    public static FoodInfoRepository getInstance(final Context context) {
        return new FoodInfoRepository(context);
    }

    private FoodInfoRepository(final Context context) {
        this.path = context.getString(R.string.path_to_product);
    }


    public void save(final FoodInfo foodInfo) {
        this.db.collection(this.path)
                .document(foodInfo.getId())
                .set(foodInfo);
    }

    public void update(final FoodInfo foodInfo) {
        this.db.collection(this.path)
                .document(foodInfo.getId())
                .set(foodInfo);
    }
}
