package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.adapter.FoodSearchAdapter;
import com.sharkit.nextmonday.main_menu.calculator.configuration.navigation.CalculatorNavigation;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.db.firebase.FoodInfoRepository;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;

import java.util.List;

public class FoodSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calculator_food_finder, container, false);
        final CalculatorWidget.FoodSearchWidget widget = CalculatorWidget.getInstance(view).getFoodSearchWidget();

        final FoodInfoRepository repository = FoodInfoRepository.getInstance(this.getContext());
        final CalculatorNavigation navigation = CalculatorNavigation.getInstance(this.getContext());

        repository.findAll()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    final List<FoodInfo> foodInfo = queryDocumentSnapshots.toObjects(FoodInfo.class);
                    final FoodSearchAdapter adapter = new FoodSearchAdapter(foodInfo, FoodSearchFragment.this.getContext());
                    widget.getListOfFood().setAdapter(adapter);
                });

        widget.getCreate().setOnClickListener(v -> navigation.moveToCreateFood());
        return view;
    }
}
