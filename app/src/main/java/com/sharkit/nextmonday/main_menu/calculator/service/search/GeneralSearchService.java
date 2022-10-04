package com.sharkit.nextmonday.main_menu.calculator.service.search;

import android.content.Context;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.main_menu.calculator.adapter.FoodSearchAdapter;
import com.sharkit.nextmonday.main_menu.calculator.db.firebase.FoodInfoRepository;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;
import com.sharkit.nextmonday.main_menu.calculator.enums.action.CalculatorSearchTypeAction;

import java.util.List;

public class GeneralSearchService implements CalculatorSearchTypeAction {

    @Override
    public void search(final String tag, final Context context, final ExpandableListView foodList) {
        final FoodInfoRepository repository = FoodInfoRepository.getInstance(context);

        repository.findAllByTag(tag).addOnSuccessListener(queryDocumentSnapshots -> {
            final List<FoodInfo> foodInfo = queryDocumentSnapshots.toObjects(FoodInfo.class);
            final FoodSearchAdapter adapter = new FoodSearchAdapter(foodInfo, context);
            foodList.setAdapter(adapter);
        });
    }
}
