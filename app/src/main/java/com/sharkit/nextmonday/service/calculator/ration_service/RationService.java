package com.sharkit.nextmonday.service.calculator.ration_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ADD;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.CREATE_NEW_MEAL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.calculator.RationAdapter;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.db.sqlite.calculator.product.ProductPFCDataService;
import com.sharkit.nextmonday.db.sqlite.calculator.ration_list.RationLinkDataService;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;
import com.sharkit.nextmonday.entity.calculator.Meal;
import com.sharkit.nextmonday.entity.calculator.RationNutrition;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.ArrayList;

public class RationService implements LayoutService {
    private final String dateRation;
    private Context context;
    private TextView date;
    private ExpandableListView listView;
    private Button createMeal;
    private ArrayList<ArrayList<GeneralDataPFCDTO>> linkFoodDTOs;
    private ArrayList<RationNutrition> rationNutrition;

    public RationService(String dateRation) {
        this.dateRation = dateRation;
    }

    @Override
    public LayoutService writeToField() {
        date.setText(dateRation);
        getRation();
        return this;
    }

    private void getRation() {

        FoodInfoFirebase foodInfoFirebase = new FoodInfoFirebase();
        foodInfoFirebase.getMealList(dateRation)
                .addOnSuccessListener(querySnapshots -> {
                    linkFoodDTOs = new ArrayList<>();
                    rationNutrition = new ArrayList<>();

                    for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshots) {

                        ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS = new ArrayList<>();
                        RationNutrition nutrition = new RationNutrition();
                        nutrition.setNameMeal(queryDocumentSnapshot.toObject(Meal.class).getName());
                        ArrayList<LinkFoodDTO> links = new RationLinkDataService(context)
                                .findAllLinkByMealAndDate(queryDocumentSnapshot.toObject(Meal.class).getName(), dateRation);

                        for (int i = 0; i < links.size(); i++) {
                            generalDataPFCDTOS.add(getItemNutrition(new ProductPFCDataService(context)
                                    .findByID(links.get(i).getLink()).transform(new GeneralDataPFCDTO()),links.get(i).getPortion()));
                        }
                        rationNutrition.add(getAllNutrition(generalDataPFCDTOS, nutrition));
                        linkFoodDTOs.add(generalDataPFCDTOS);
                    }
                    listView.setAdapter(new RationAdapter(rationNutrition, linkFoodDTOs, context));
                });
    }

    private GeneralDataPFCDTO getItemNutrition(GeneralDataPFCDTO generalDataPFCDTO, float portion) {
        NutritionService nutritionService = new NutritionService();
        generalDataPFCDTO.setCalorie(nutritionService.getCalorie(generalDataPFCDTO, portion));
        generalDataPFCDTO.setCarbohydrate(nutritionService.getCarbohydrate(generalDataPFCDTO, portion));
        generalDataPFCDTO.setFat(nutritionService.getFat(generalDataPFCDTO, portion));
        generalDataPFCDTO.setProtein(nutritionService.getProtein(generalDataPFCDTO, portion));
        return generalDataPFCDTO;
    }

    private RationNutrition getAllNutrition(ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS, RationNutrition nutrition) {
        NutritionService nutritionService = new NutritionService(generalDataPFCDTOS);
        nutrition.setCalorie(nutritionService.getCalorie());
        nutrition.setCarbohydrate(nutritionService.getCarbohydrate());
        nutrition.setFat(nutritionService.getFat());
        nutrition.setProtein(nutritionService.getProtein());
        return nutrition;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        createMeal.setOnClickListener(view -> createAlertMeal());
        return this;
    }

    @SuppressLint("InflateParams")
    private void createAlertMeal() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.calculator_alert_add_meal, null);
        EditText text = view.findViewById(R.id.meal_xml);
        dialog.setPositiveButton(ADD, (dialogInterface, i) -> {
            Meal meal = new Meal(text.getText().toString().trim());
            new FoodInfoFirebase().createMeal(meal)
                    .addOnSuccessListener(unused -> Toast.makeText(context, CREATE_NEW_MEAL, Toast.LENGTH_SHORT).show());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_ration);
        }).setOnCancelListener(DialogInterface::dismiss)
                .setView(view)
                .show();
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        listView = root.findViewById(R.id.ration_xml);
        createMeal = root.findViewById(R.id.create_meal_xml);
        date = root.findViewById(R.id.date_xml);
        return this;
    }
}
