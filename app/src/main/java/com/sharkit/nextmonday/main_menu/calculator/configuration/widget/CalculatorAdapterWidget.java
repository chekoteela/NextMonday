package com.sharkit.nextmonday.main_menu.calculator.configuration.widget;

import android.view.View;
import android.widget.LinearLayout;
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

    public FoodInfoItemWidget getFoodInfoItemWidget() {
        return new FoodInfoItemWidget();
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

    @Getter
    public class FoodInfoItemWidget {

        private final TextView calorie;
        private final TextView protein;
        private final TextView wheyProtein;
        private final TextView soyProtein;
        private final TextView aggProtein;
        private final TextView caseinProtein;
        private final TextView carbohydrate;
        private final TextView simpleCarbohydrate;
        private final TextView complexCarbohydrate;
        private final TextView fat;
        private final TextView saturatedFat;
        private final TextView transFat;
        private final TextView omega9;
        private final TextView omega6;
        private final TextView omega3;
        private final TextView ala;
        private final TextView epa;
        private final TextView dha;
        private final TextView water;
        private final TextView cellulose;
        private final TextView salt;
        private final TextView calcium;
        private final TextView potassium;

        private final LinearLayout calorieLayout;
        private final LinearLayout proteinLayout;
        private final LinearLayout wheyProteinLayout;
        private final LinearLayout soyProteinLayout;
        private final LinearLayout aggProteinLayout;
        private final LinearLayout caseinProteinLayout;
        private final LinearLayout carbohydrateLayout;
        private final LinearLayout simpleCarbohydrateLayout;
        private final LinearLayout complexCarbohydrateLayout;
        private final LinearLayout fatLayout;
        private final LinearLayout saturatedFatLayout;
        private final LinearLayout transFatLayout;
        private final LinearLayout omega9Layout;
        private final LinearLayout omega6Layout;
        private final LinearLayout omega3Layout;
        private final LinearLayout alaLayout;
        private final LinearLayout epaLayout;
        private final LinearLayout dhaLayout;
        private final LinearLayout waterLayout;
        private final LinearLayout celluloseLayout;
        private final LinearLayout saltLayout;
        private final LinearLayout calciumLayout;
        private final LinearLayout potassiumLayout;

        public FoodInfoItemWidget() {
            this.calorieLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.calorie_layout_id);
            this.proteinLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.protein_layout_id);
            this.wheyProteinLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.whey_protein_layout_id);
            this.soyProteinLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.soy_protein_layout_id);
            this.caseinProteinLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.casein_protein_layout_id);
            this.aggProteinLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.agg_protein_layout_id);
            this.carbohydrateLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.carbohydrate_layout_id);
            this.simpleCarbohydrateLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.simple_carbohydrate_layout_id);
            this.complexCarbohydrateLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.complex_carbohydrate_layout_id);
            this.fatLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.fat_layout_id);
            this.saturatedFatLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.saturated_fat_layout_id);
            this.transFatLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.trans_fat_layout_id);
            this.omega9Layout = CalculatorAdapterWidget.this.view.findViewById(R.id.omega9_layout_id);
            this.omega6Layout = CalculatorAdapterWidget.this.view.findViewById(R.id.omega6_layout_id);
            this.omega3Layout = CalculatorAdapterWidget.this.view.findViewById(R.id.omega3_layout_id);
            this.dhaLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.dha_layout_id);
            this.epaLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.epa_layout_id);
            this.alaLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.ala_layout_id);
            this.waterLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.water_layout_id);
            this.celluloseLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.cellulose_layout_id);
            this.saltLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.salt_layout_id);
            this.calciumLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.calcium_layout_id);
            this.potassiumLayout = CalculatorAdapterWidget.this.view.findViewById(R.id.potassium_layout_id);

            this.calorie = CalculatorAdapterWidget.this.view.findViewById(R.id.calorie_id);
            this.protein = CalculatorAdapterWidget.this.view.findViewById(R.id.protein_id);
            this.wheyProtein = CalculatorAdapterWidget.this.view.findViewById(R.id.whey_protein_id);
            this.soyProtein = CalculatorAdapterWidget.this.view.findViewById(R.id.soy_protein_id);
            this.caseinProtein = CalculatorAdapterWidget.this.view.findViewById(R.id.casein_protein_id);
            this.aggProtein = CalculatorAdapterWidget.this.view.findViewById(R.id.agg_protein_id);
            this.carbohydrate = CalculatorAdapterWidget.this.view.findViewById(R.id.carbohydrate_id);
            this.simpleCarbohydrate = CalculatorAdapterWidget.this.view.findViewById(R.id.simple_carbohydrate_id);
            this.complexCarbohydrate = CalculatorAdapterWidget.this.view.findViewById(R.id.complex_carbohydrate_id);
            this.fat = CalculatorAdapterWidget.this.view.findViewById(R.id.fat_id);
            this.saturatedFat = CalculatorAdapterWidget.this.view.findViewById(R.id.saturated_fat_id);
            this.transFat = CalculatorAdapterWidget.this.view.findViewById(R.id.trans_fat_id);
            this.omega9 = CalculatorAdapterWidget.this.view.findViewById(R.id.omega9_id);
            this.omega6 = CalculatorAdapterWidget.this.view.findViewById(R.id.omega6_id);
            this.omega3 = CalculatorAdapterWidget.this.view.findViewById(R.id.omega3_id);
            this.dha = CalculatorAdapterWidget.this.view.findViewById(R.id.dha_id);
            this.epa = CalculatorAdapterWidget.this.view.findViewById(R.id.epa_id);
            this.ala = CalculatorAdapterWidget.this.view.findViewById(R.id.ala_id);
            this.water = CalculatorAdapterWidget.this.view.findViewById(R.id.water_id);
            this.cellulose = CalculatorAdapterWidget.this.view.findViewById(R.id.cellulose_id);
            this.salt = CalculatorAdapterWidget.this.view.findViewById(R.id.salt_id);
            this.calcium = CalculatorAdapterWidget.this.view.findViewById(R.id.calcium_id);
            this.potassium = CalculatorAdapterWidget.this.view.findViewById(R.id.potassium_id);
        }
    }
}
