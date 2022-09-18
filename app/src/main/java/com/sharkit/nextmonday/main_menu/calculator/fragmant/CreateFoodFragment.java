package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.db.firebase.FoodInfoRepository;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Carbohydrate;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Fat;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Omega3;
import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.Protein;
import com.sharkit.nextmonday.main_menu.calculator.service.CreateFoodValidator;

import java.util.UUID;

public class CreateFoodFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calculator_create_food, container, false);
        final CalculatorWidget.CreateFoodWidget widget = CalculatorWidget.getInstance(view).getCreateFoodWidget();
        final FoodInfo foodInfo = this.buildSkeletonFoodInfo();
        final CreateFoodValidator validator = new CreateFoodValidator(widget, foodInfo, this.getContext());

        validator.execute();

        widget.getSave().setOnClickListener(v -> {
            if (Boolean.FALSE.equals(validator.isValidField())) {
                return;
            }
            FoodInfoRepository.getInstance(CreateFoodFragment.this.getContext()).save(foodInfo);
        });

        return view;
    }

    private FoodInfo buildSkeletonFoodInfo() {
        return FoodInfo.builder()
                .id(UUID.randomUUID().toString())
                .carbohydrate(Carbohydrate.builder().build())
                .protein(Protein.builder().build())
                .fat(Fat.builder()
                        .omega3(Omega3.builder().build())
                        .build())
                .build();

    }
}
