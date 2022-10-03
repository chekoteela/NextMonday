package com.sharkit.nextmonday.main_menu.calculator.configuration.widget;

import android.view.View;
import android.widget.TextView;

import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculatorAdapterWidget {

    private final View view;

    public ParentFoodItemWidget getParentFoodItemWidget() {
        return new ParentFoodItemWidget();
    }

    public static CalculatorAdapterWidget getInstance(final View view) {
        return new CalculatorAdapterWidget(view);
    }

    @Getter
    public class ParentFoodItemWidget {

        private final TextView foodName;
        private final TextView protein;
        private final TextView carbohydrate;
        private final TextView fat;
        private final TextView calorie;

        public ParentFoodItemWidget() {
            this.foodName = CalculatorAdapterWidget.this.view.findViewById(R.id.food_name_id);
            this.protein = CalculatorAdapterWidget.this.view.findViewById(R.id.protein_id);
            this.carbohydrate = CalculatorAdapterWidget.this.view.findViewById(R.id.carbohydrate_id);
            this.fat = CalculatorAdapterWidget.this.view.findViewById(R.id.fat_id);
            this.calorie = CalculatorAdapterWidget.this.view.findViewById(R.id.calorie_id);
        }
    }
}
