package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import static com.sharkit.nextmonday.main_menu.calculator.configuration.CalculatorBundleTag.CALCULATOR_PRODUCT_FOR_UPDATE;

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
import com.sharkit.nextmonday.main_menu.calculator.service.CreateFoodValidator;

import java.util.Optional;

public class UpdateFoodFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calculator_create_food, container, false);
        final CalculatorWidget.CreateFoodWidget widget = CalculatorWidget.getInstance(view).getCreateFoodWidget();
        final FoodInfo foodInfo = (FoodInfo) this.requireArguments().getSerializable(CALCULATOR_PRODUCT_FOR_UPDATE);
        final CreateFoodValidator validator = new CreateFoodValidator(widget, foodInfo, this.getContext());

        this.fillOutAllTheFields(foodInfo, widget);

        validator.execute();

        widget.getSave().setOnClickListener(v -> {
            if (Boolean.FALSE.equals(validator.isValidField())) {
                return;
            }
            FoodInfoRepository.getInstance(UpdateFoodFragment.this.getContext()).update(foodInfo);
        });

        return view;
    }

    private void fillOutAllTheFields(final FoodInfo foodInfo, final CalculatorWidget.CreateFoodWidget widget) {
        widget.getNameOfFood().setText(foodInfo.getName());
        widget.getPortion().setText(this.getValueFromObject(foodInfo.getPortion()));
        widget.getCalorie().setText(this.getValueFromObject(foodInfo.getCalorie()));
        widget.getCalcium().setText(this.getValueFromObject(foodInfo.getCalcium()));
        widget.getWater().setText(this.getValueFromObject(foodInfo.getWater()));
        widget.getSalt().setText(this.getValueFromObject(foodInfo.getSalt()));
        widget.getPotassium().setText(this.getValueFromObject(foodInfo.getPotassium()));
        widget.getCellulose().setText(this.getValueFromObject(foodInfo.getCellulose()));

        widget.getProteinWidget().getProtein().setText(this.getValueFromObject(foodInfo.getProtein().getGeneralProteinWeight()));
        widget.getProteinWidget().getWheyProtein().setText(this.getValueFromObject(foodInfo.getProtein().getWheyProtein()));
        widget.getProteinWidget().getSoyProtein().setText(this.getValueFromObject(foodInfo.getProtein().getSoyProtein()));
        widget.getProteinWidget().getAggProtein().setText(this.getValueFromObject(foodInfo.getProtein().getAggProtein()));
        widget.getProteinWidget().getCaseinProtein().setText(this.getValueFromObject(foodInfo.getProtein().getCaseinProtein()));

        widget.getCarbohydrateWidget().getCarbohydrate().setText(this.getValueFromObject(foodInfo.getCarbohydrate().getGeneralCarbohydrateWeight()));
        widget.getCarbohydrateWidget().getComplexCarbohydrate().setText(this.getValueFromObject(foodInfo.getCarbohydrate().getComplexCarbohydrate()));
        widget.getCarbohydrateWidget().getSimpleCarbohydrate().setText(this.getValueFromObject(foodInfo.getCarbohydrate().getSimpleCarbohydrate()));

        widget.getFatWidget().getFat().setText(this.getValueFromObject(foodInfo.getFat().getGeneralFatWeight()));
        widget.getFatWidget().getTransFat().setText(this.getValueFromObject(foodInfo.getFat().getTransFat()));
        widget.getFatWidget().getSaturatedFat().setText(this.getValueFromObject(foodInfo.getFat().getSaturatedFat()));
        widget.getFatWidget().getOmega9().setText(this.getValueFromObject(foodInfo.getFat().getOmega9()));
        widget.getFatWidget().getOmega6().setText(this.getValueFromObject(foodInfo.getFat().getOmega6()));
        widget.getFatWidget().getOmega3Widget().getOmega3().setText(this.getValueFromObject(foodInfo.getFat().getOmega3().getOmega3()));
        widget.getFatWidget().getOmega3Widget().getAla().setText(this.getValueFromObject(foodInfo.getFat().getOmega3().getAla()));
        widget.getFatWidget().getOmega3Widget().getEpa().setText(this.getValueFromObject(foodInfo.getFat().getOmega3().getEpa()));
        widget.getFatWidget().getOmega3Widget().getDha().setText(this.getValueFromObject(foodInfo.getFat().getOmega3().getDha()));

    }

    private String getValueFromObject(final Integer value) {
        return Optional.of(value)
                .filter(v -> !v.equals(0))
                .map(String::valueOf)
                .orElse("");
    }

    private String getValueFromObject(final Float value) {
        return Optional.of(value)
                .filter(v -> !v.equals(0.0f))
                .map(String::valueOf)
                .orElse("");
    }
}
