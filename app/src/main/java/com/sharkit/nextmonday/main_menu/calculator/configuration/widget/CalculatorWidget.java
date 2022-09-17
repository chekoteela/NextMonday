package com.sharkit.nextmonday.main_menu.calculator.configuration.widget;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.progress.progressview.ProgressView;
import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculatorWidget {

    private final View view;

    public MainWidget getMainWidget() {
        return new MainWidget();
    }

    public AddMealWidget getAddMealWidget() {
        return new AddMealWidget();
    }

    public WeightWidget getWeightWidget() {
        return new WeightWidget();
    }

    public RationWidget getRationWidget() {
        return new RationWidget();
    }

    public FoodFinderWidget getFoodFinderWidget() {
        return new FoodFinderWidget();
    }

    public CreateFoodWidget getCreateFoodWidget() {
        return new CreateFoodWidget();
    }

    public static CalculatorWidget getInstance(final View view) {
        return new CalculatorWidget(view);
    }

    @Getter
    public class MainWidget {

        private final ProgressView calorieProgress;
        private final ProgressView fatProgress;
        private final ProgressView carbohydrateProgress;
        private final ProgressView proteinProgress;
        private final ProgressView waterProgress;

        private final TextView percentCalorie;
        private final TextView percentFat;
        private final TextView percentCarbohydrate;
        private final TextView percentProtein;
        private final TextView percentWater;

        private final TextView eatenCalorie;
        private final TextView eatenFat;
        private final TextView eatenCarbohydrate;
        private final TextView eatenProtein;
        private final TextView drinkWater;

        private final TextView allEatenCalorie;
        private final TextView allEatenFat;
        private final TextView allEatenCarbohydrate;
        private final TextView allEatenProtein;
        private final TextView allDrinkWater;

        private final TextView allCalorie;
        private final TextView allFat;
        private final TextView allCarbohydrate;
        private final TextView allProtein;
        private final TextView allWater;

        private final ImageView addFood;
        private final ImageView addWater;
        private final ImageView addWeight;

        public MainWidget() {
            this.calorieProgress = CalculatorWidget.this.view.findViewById(R.id.calorie_progress_id);
            this.fatProgress = CalculatorWidget.this.view.findViewById(R.id.folder_parent_id);
            this.carbohydrateProgress = CalculatorWidget.this.view.findViewById(R.id.carbohydrate_progress_id);
            this.proteinProgress = CalculatorWidget.this.view.findViewById(R.id.protein_progress_id);
            this.waterProgress = CalculatorWidget.this.view.findViewById(R.id.water_progress_id);

            this.percentCalorie = CalculatorWidget.this.view.findViewById(R.id.percent_calorie_id);
            this.percentFat = CalculatorWidget.this.view.findViewById(R.id.percent_fat_id);
            this.percentCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.percent_carbohydrate_id);
            this.percentProtein = CalculatorWidget.this.view.findViewById(R.id.percent_protein_id);
            this.percentWater = CalculatorWidget.this.view.findViewById(R.id.percent_water_id);

            this.eatenCalorie = CalculatorWidget.this.view.findViewById(R.id.eaten_calorie_id);
            this.eatenFat = CalculatorWidget.this.view.findViewById(R.id.eaten_fat_id);
            this.eatenCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.eaten_carbohydrate_id);
            this.eatenProtein = CalculatorWidget.this.view.findViewById(R.id.eaten_protein_id);
            this.drinkWater = CalculatorWidget.this.view.findViewById(R.id.drink_water_id);

            this.allEatenCalorie = CalculatorWidget.this.view.findViewById(R.id.all_eaten_calorie_id);
            this.allEatenFat = CalculatorWidget.this.view.findViewById(R.id.all_eaten_fat_id);
            this.allEatenCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.all_eaten_carbohydrate_id);
            this.allEatenProtein = CalculatorWidget.this.view.findViewById(R.id.all_eaten_protein_id);
            this.allDrinkWater = CalculatorWidget.this.view.findViewById(R.id.all_drink_water_id);

            this.allCalorie = CalculatorWidget.this.view.findViewById(R.id.all_calorie_id);
            this.allFat = CalculatorWidget.this.view.findViewById(R.id.all_fat_id);
            this.allCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.all_carbohydrate_id);
            this.allProtein = CalculatorWidget.this.view.findViewById(R.id.all_protein_id);
            this.allWater = CalculatorWidget.this.view.findViewById(R.id.all_water_id);

            this.addFood = CalculatorWidget.this.view.findViewById(R.id.add_food_id);
            this.addWater = CalculatorWidget.this.view.findViewById(R.id.add_water_id);
            this.addWeight = CalculatorWidget.this.view.findViewById(R.id.add_weight_id);

        }
    }

    @Getter
    public class AddMealWidget {

        private final TextView nameOfFood;
        private final TextView nameOfMeal;
        private final TextView calorieSum;
        private final TextView allWeightOfFood;
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
        private final TextView omega3;
        private final TextView omega6;
        private final TextView omega9;
        private final TextView ala;
        private final TextView epa;
        private final TextView dha;
        private final TextView water;
        private final TextView cellulose;
        private final TextView salt;
        private final TextView calcium;
        private final TextView potassium;

        private final ImageView asFavorite;
        private final EditText countOfMeal;
        private final SwitchCompat weight;
        private final Spinner mealSpinner;
        private final Button save;
        private final Button update;

        public AddMealWidget() {

            this.nameOfFood = CalculatorWidget.this.view.findViewById(R.id.name_of_food_id);
            this.nameOfMeal = CalculatorWidget.this.view.findViewById(R.id.name_of_meal_id);
            this.calorieSum = CalculatorWidget.this.view.findViewById(R.id.calorie_sum_id);
            this.allWeightOfFood = CalculatorWidget.this.view.findViewById(R.id.all_weight_of_food_id);
            this.protein = CalculatorWidget.this.view.findViewById(R.id.protein_id);
            this.wheyProtein = CalculatorWidget.this.view.findViewById(R.id.whey_protein_id);
            this.soyProtein = CalculatorWidget.this.view.findViewById(R.id.soy_protein_id);
            this.aggProtein = CalculatorWidget.this.view.findViewById(R.id.all_protein_id);
            this.caseinProtein = CalculatorWidget.this.view.findViewById(R.id.casein_protein_id);
            this.carbohydrate = CalculatorWidget.this.view.findViewById(R.id.carbohydrate_id);
            this.simpleCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.simple_carbohydrates_id);
            this.complexCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.complex_carbohydrate_id);
            this.fat = CalculatorWidget.this.view.findViewById(R.id.fat_id);
            this.saturatedFat = CalculatorWidget.this.view.findViewById(R.id.saturated_fat_id);
            this.transFat = CalculatorWidget.this.view.findViewById(R.id.trans_fat_id);
            this.omega3 = CalculatorWidget.this.view.findViewById(R.id.omega3_id);
            this.omega6 = CalculatorWidget.this.view.findViewById(R.id.omega6_id);
            this.omega9 = CalculatorWidget.this.view.findViewById(R.id.omega9_id);
            this.ala = CalculatorWidget.this.view.findViewById(R.id.ala_id);
            this.epa = CalculatorWidget.this.view.findViewById(R.id.epa_id);
            this.dha = CalculatorWidget.this.view.findViewById(R.id.dha_id);
            this.water = CalculatorWidget.this.view.findViewById(R.id.water_id);
            this.cellulose = CalculatorWidget.this.view.findViewById(R.id.cellulose_id);
            this.salt = CalculatorWidget.this.view.findViewById(R.id.salt_id);
            this.calcium = CalculatorWidget.this.view.findViewById(R.id.calcium_id);
            this.potassium = CalculatorWidget.this.view.findViewById(R.id.potassium_id);

            this.asFavorite = CalculatorWidget.this.view.findViewById(R.id.as_favorite_id);
            this.countOfMeal = CalculatorWidget.this.view.findViewById(R.id.count_of_meal_id);
            this.weight = CalculatorWidget.this.view.findViewById(R.id.weight_id);
            this.mealSpinner = CalculatorWidget.this.view.findViewById(R.id.meal_spinner_id);
            this.save = CalculatorWidget.this.view.findViewById(R.id.save_id);
            this.update = CalculatorWidget.this.view.findViewById(R.id.update_food_id);
        }
    }

    @Getter
    public class CreateFoodWidget {

        private final EditText nameOfFood;
        private final EditText calorie;
        private final EditText portion;
        private final EditText water;
        private final EditText cellulose;
        private final EditText salt;
        private final EditText calcium;
        private final EditText potassium;
        private final Button save;

        private final FatWidget fatWidget;
        private final CarbohydrateWidget carbohydrateWidget;
        private final ProteinWidget proteinWidget;

        public CreateFoodWidget() {
            this.nameOfFood = CalculatorWidget.this.view.findViewById(R.id.name_of_food_id);
            this.calorie = CalculatorWidget.this.view.findViewById(R.id.calorie_id);
            this.portion = CalculatorWidget.this.view.findViewById(R.id.portion_id);
            this.water = CalculatorWidget.this.view.findViewById(R.id.water_id);
            this.cellulose = CalculatorWidget.this.view.findViewById(R.id.cellulose_id);
            this.salt = CalculatorWidget.this.view.findViewById(R.id.salt_id);
            this.calcium = CalculatorWidget.this.view.findViewById(R.id.calcium_id);
            this.potassium = CalculatorWidget.this.view.findViewById(R.id.potassium_id);
            this.save = CalculatorWidget.this.view.findViewById(R.id.save_id);

            this.fatWidget = new FatWidget();
            this.carbohydrateWidget = new CarbohydrateWidget();
            this.proteinWidget = new ProteinWidget();
        }

        @Getter
        public class FatWidget {

            private final EditText fat;
            private final EditText saturatedFat;
            private final EditText transFat;
            private final EditText omega9;
            private final EditText omega6;
            private final Omega3Widget omega3Widget;

            public FatWidget() {
                this.fat = CalculatorWidget.this.view.findViewById(R.id.fat_id);
                this.saturatedFat = CalculatorWidget.this.view.findViewById(R.id.saturated_fat_id);
                this.transFat = CalculatorWidget.this.view.findViewById(R.id.trans_fat_id);
                this.omega9 = CalculatorWidget.this.view.findViewById(R.id.omega9_id);
                this.omega6 = CalculatorWidget.this.view.findViewById(R.id.omega6_id);

                this.omega3Widget = new Omega3Widget();
            }

            @Getter
            public class Omega3Widget {

                private final EditText omega3;
                private final EditText ala;
                private final EditText epa;
                private final EditText dha;

                public Omega3Widget() {
                    this.omega3 = CalculatorWidget.this.view.findViewById(R.id.omega3_id);
                    this.ala = CalculatorWidget.this.view.findViewById(R.id.ala_id);
                    this.epa = CalculatorWidget.this.view.findViewById(R.id.epa_id);
                    this.dha = CalculatorWidget.this.view.findViewById(R.id.dha_id);
                }
            }
        }

        @Getter
        public class CarbohydrateWidget {

            private final EditText carbohydrate;
            private final EditText simpleCarbohydrate;
            private final EditText complexCarbohydrate;

            public CarbohydrateWidget(){
                this.carbohydrate = CalculatorWidget.this.view.findViewById(R.id.carbohydrate_id);
                this.simpleCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.simple_carbohydrates_id);
                this.complexCarbohydrate = CalculatorWidget.this.view.findViewById(R.id.complex_carbohydrate_id);
            }
        }

        @Getter
        public class ProteinWidget {

            private final EditText protein;
            private final EditText wheyProtein;
            private final EditText soyProtein;
            private final EditText aggProtein;
            private final EditText caseinProtein;

            public ProteinWidget() {
                this.protein = CalculatorWidget.this.view.findViewById(R.id.protein_id);
                this.wheyProtein = CalculatorWidget.this.view.findViewById(R.id.whey_protein_id);
                this.soyProtein = CalculatorWidget.this.view.findViewById(R.id.soy_protein_id);
                this.aggProtein = CalculatorWidget.this.view.findViewById(R.id.agg_protein_id);
                this.caseinProtein = CalculatorWidget.this.view.findViewById(R.id.casein_protein_id);
            }
        }
    }

    @Getter
    public class FoodFinderWidget {

        private final TabLayout tabLayout;
        private final EditText findFood;
        private final ExpandableListView listOfFood;
        private final Button create;

        public FoodFinderWidget() {
            this.tabLayout = CalculatorWidget.this.view.findViewById(R.id.food_finder_tab_layout_id);
            this.findFood = CalculatorWidget.this.view.findViewById(R.id.find_food_id);
            this.listOfFood = CalculatorWidget.this.view.findViewById(R.id.list_of_food_id);
            this.create = CalculatorWidget.this.view.findViewById(R.id.create_id);
        }
    }

    @Getter
    public class RationWidget {

        private final TextView date;
        private final ExpandableListView rationList;
        private final Button create;

        public RationWidget() {
            this.date = CalculatorWidget.this.view.findViewById(R.id.date_id);
            this.rationList = CalculatorWidget.this.view.findViewById(R.id.ration_list_id);
            this.create = CalculatorWidget.this.view.findViewById(R.id.create_id);
        }
    }

    @Getter
    public class WeightWidget {

        private final TextView currentWeight;
        private final TextView desiredWeight;
        private final GraphView graphWeight;
        private final ListView weightList;
        private final Button create;

        public WeightWidget() {
            this.currentWeight = CalculatorWidget.this.view.findViewById(R.id.current_weight_id);
            this.desiredWeight = CalculatorWidget.this.view.findViewById(R.id.desired_weight_id);
            this.graphWeight = CalculatorWidget.this.view.findViewById(R.id.graph_weight_id);
            this.weightList = CalculatorWidget.this.view.findViewById(R.id.list_weight_id);
            this.create = CalculatorWidget.this.view.findViewById(R.id.create_id);
        }
    }

    @Getter
    public class AutomateCalculatorWidget {
        private final TabLayout typeOfCalculator;
        private final EditText currentWeight;
        private final EditText age;
        private final EditText desiredWeight;
        private final EditText height;
        private final EditText weight;
        private final EditText protein;
        private final EditText carbohydrate;
        private final EditText fat;
        private final EditText water;
        private final RadioGroup sex;
        private final RadioGroup formula;
        private final SmartMaterialSpinner target;
        private final SmartMaterialSpinner activity;
        private final Button calculate;
        private final Button save;
        private final TextView conclusion;
        private final ScrollView automateCalculator;
        private final ScrollView manualCalculator;

        public AutomateCalculatorWidget() {
            this.typeOfCalculator = CalculatorWidget.this.view.findViewById(R.id.type_of_calculator_id);
            this.currentWeight = CalculatorWidget.this.view.findViewById(R.id.current_weight_id);
            this.age = CalculatorWidget.this.view.findViewById(R.id.age_id);
            this.desiredWeight = CalculatorWidget.this.view.findViewById(R.id.desired_weight_id);
            this.height = CalculatorWidget.this.view.findViewById(R.id.height_id);
            this.weight = CalculatorWidget.this.view.findViewById(R.id.weight_id);
            this.protein = CalculatorWidget.this.view.findViewById(R.id.protein_id);
            this.carbohydrate = CalculatorWidget.this.view.findViewById(R.id.carbohydrate_id);
            this.fat = CalculatorWidget.this.view.findViewById(R.id.fat_id);
            this.water = CalculatorWidget.this.view.findViewById(R.id.water_id);
            this.sex = CalculatorWidget.this.view.findViewById(R.id.sex_id);
            this.formula = CalculatorWidget.this.view.findViewById(R.id.formula_id);
            this.target = CalculatorWidget.this.view.findViewById(R.id.target_id);
            this.activity = CalculatorWidget.this.view.findViewById(R.id.activity_id);
            this.calculate = CalculatorWidget.this.view.findViewById(R.id.calculate_id);
            this.save = CalculatorWidget.this.view.findViewById(R.id.save_id);
            this.conclusion = CalculatorWidget.this.view.findViewById(R.id.conclusion_id);
            this.automateCalculator = CalculatorWidget.this.view.findViewById(R.id.auto_calculator_id);
            this.manualCalculator = CalculatorWidget.this.view.findViewById(R.id.manual_calculator_id);

        }
    }
}
