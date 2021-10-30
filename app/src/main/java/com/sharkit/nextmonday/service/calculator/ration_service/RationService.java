package com.sharkit.nextmonday.service.calculator.ration_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ADD;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.CREATE_NEW_MEAL;

import android.annotation.SuppressLint;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.calculator.RationAdapter;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;
import com.sharkit.nextmonday.entity.calculator.Meal;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.ArrayList;
import java.util.Objects;

public class RationService implements LayoutService {
    private final String dateRation;
    private Context context;
    private TextView date;
    private ExpandableListView listView;
    private Button createMeal;
    private ArrayList<ArrayList<GeneralDataPFCDTO>> linkFoodDTOs;

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
        foodInfoFirebase.getMealList()
                .addOnSuccessListener(querySnapshots -> {
                    linkFoodDTOs = new ArrayList<>();

                    for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshots) {
                        ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS = new ArrayList<>();
                        foodInfoFirebase.getCurrentMeal(queryDocumentSnapshot.toObject(Meal.class).getName())
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        foodInfoFirebase.findFoodById(documentSnapshot.toObject(LinkFoodDTO.class).getLink())
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        generalDataPFCDTOS.add(Objects.requireNonNull(documentSnapshot.toObject(FoodInfo.class)).transform(new GeneralDataPFCDTO()));
                                                    }
                                                });
                                    }
                                });
                        linkFoodDTOs.add(generalDataPFCDTOS);
                    }
                    listView.setAdapter(new RationAdapter(linkFoodDTOs, context));

                });
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
