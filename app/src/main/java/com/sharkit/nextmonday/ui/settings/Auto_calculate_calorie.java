package com.sharkit.nextmonday.ui.settings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.MySQL.MyWeight;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.SettingsCalculator;
import com.sharkit.nextmonday.variables.WeightV;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Auto_calculate_calorie extends Fragment {
    float pos = 1;
    int change_weight = 0;

    final String TAG = "qwerty";

    int calorie, age_v, height_v;

    String sex, target, formula;

    EditText weight, height, age, desired_weight;

    RadioButton male, female, harrison_formula, muffin_formula;

    Button to_calculate, save;

    Spinner spinner, spinner_target;

    TextView conclusion, formula_info;

    TabLayout tabLayout;

    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference users;

    SQLiteDatabase sdb;
    MyWeight myWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.calculator_auto_calculate_calorie, container, false);

        FindView(root);




        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.spinner_target,R.layout.support_simple_spinner_dropdown_item);
        spinner_target.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.spinner_activity,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        formula_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.spinner_item, null);
                TextView textView = existProduct.findViewById(R.id.text_xml);
                textView.setText("Изменить желаемый вес");
                dialog.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });
        spinner_target.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        change_weight = -300;
                        desired_weight.setEnabled(true);
                        target = "lose_weight";
                        break;
                    case 2:
                        change_weight = 300;
                        desired_weight.setEnabled(true);
                        target = "gain_weight";
                        break;
                    case 1:
                        change_weight = 0;
                        desired_weight.setEnabled(false);
                        desired_weight.setText(weight.getText().toString());
                        target = "hold_the_weight";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        navController.navigate(R.id.nav_cal_auto_calculate_calorie);
                        break;
                    case 1:
                        navController.navigate(R.id.nav_settings_manual_calculate);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        to_calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(weight.getText())){
                    Toast.makeText(getContext(),"Введите ваш текщий вес",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Float.parseFloat(weight.getText().toString()) > 1000 || Float.parseFloat(weight.getText().toString()) < 10){
                    Toast.makeText(getContext(), "Введите корректный вес", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(age.getText())){
                    Toast.makeText(getContext(),"Введите ваш возраст",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Float.parseFloat(age.getText().toString()) > 120 || Float.parseFloat(age.getText().toString()) < 5){
                    Toast.makeText(getContext(), "Введите корректный возраст", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(height.getText())){
                    Toast.makeText(getContext(),"Введите ваш рост",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Float.parseFloat(height.getText().toString()) > 300 || Float.parseFloat(height.getText().toString()) < 30){
                    Toast.makeText(getContext(), "Введите корректный рост", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!male.isChecked() && !female.isChecked()){
                    Toast.makeText(getContext(),"Выберете ваш пол",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner_target.getSelectedItemPosition() == -1){
                    Toast.makeText(getContext(),"Выберете вашу цель",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner_target.getSelectedItemPosition() == 0 || spinner_target.getSelectedItemPosition() == 2){
                    if (TextUtils.isEmpty(desired_weight.getText())){
                        Toast.makeText(getContext(),"Введите желаемый вес",Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if (Float.parseFloat(desired_weight.getText().toString()) > 400 || Float.parseFloat(desired_weight.getText().toString()) < 10){
                        Toast.makeText(getContext(), "Введите корректный желаемый вес", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(spinner_target.getSelectedItemPosition() == 2 && Float.parseFloat(desired_weight.getText().toString()) < Float.parseFloat(weight.getText().toString())){
                    Toast.makeText(getContext(),"При наборе желаемый вес должен быть больше текщего",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner_target.getSelectedItemPosition() == 0 && Float.parseFloat(desired_weight.getText().toString()) > Float.parseFloat(weight.getText().toString())){
                    Toast.makeText(getContext(),"При похудении желаемый вес должен быть меньше текущего",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(muffin_formula.isChecked() && female.isChecked()){
                   calorie = (int) ((((10 * Float.parseFloat(weight.getText().toString())) + (6.25 * Float.parseFloat(height.getText().toString())) -
                                                (5 * Float.parseFloat(age.getText().toString())) - 161) * pos) + change_weight);
                    sex = "female";
                    formula = "muffin";
                    conclusion.setText(calorie + " ккл");
                }
                if (muffin_formula.isChecked() && male.isChecked()){
                    calorie = (int) ((((10 * Float.parseFloat(weight.getText().toString())) + (6.25 * Float.parseFloat(height.getText().toString())) -
                            (5 * Float.parseFloat(age.getText().toString())) + 5) * pos) + change_weight);
                    sex = "male";
                    formula = "muffin";
                    conclusion.setText(calorie + " ккл");
                }
                if (harrison_formula.isChecked() && female.isChecked()){
                    calorie = (int) ((((9.247 * Float.parseFloat(weight.getText().toString())) + (3.098 * Float.parseFloat(height.getText().toString())) -
                            (4.330 * Float.parseFloat(age.getText().toString())) + 447.593) * pos) + change_weight);
                    sex = "female";
                    formula = "harrison";
                    conclusion.setText(calorie + " ккл");
                }
                if(harrison_formula.isChecked() && male.isChecked()){
                    calorie = (int) ((((13.397 * Float.parseFloat(weight.getText().toString())) + (4.799 * Float.parseFloat(height.getText().toString())) -
                                                (5.677 * Float.parseFloat(age.getText().toString())) + 88.362) * pos) + change_weight);
                    sex = "male";
                    formula = "harrison";
                    conclusion.setText(calorie + " ккл");
                }
                save.setVisibility(View.VISIBLE);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        pos = 1;
                        break;
                    case 1:
                        pos = 1.2f;
                        break;
                    case 2:
                        pos = 1.375f;
                        break;
                    case 3:
                        pos = 1.55f;
                        break;
                    case 4:
                        pos = 1.725f;
                        break;
                    case 5:
                        pos = 1.9f;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pos = 1;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(weight.getText())){
                    Toast.makeText(getContext(),"Введите ваш текщий вес",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(age.getText())){
                    Toast.makeText(getContext(),"Введите ваш возраст",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(height.getText())){
                    Toast.makeText(getContext(),"Введите ваш рост",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!male.isChecked() && !female.isChecked()){
                    Toast.makeText(getContext(),"Выберете ваш пол",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner_target.getSelectedItemPosition() == -1){
                    Toast.makeText(getContext(),"Выберете вашу цель",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner_target.getSelectedItemPosition() == 0 || spinner_target.getSelectedItemPosition() == 2){
                    if (TextUtils.isEmpty(desired_weight.getText())){
                        Toast.makeText(getContext(),"Введите желаемый вес",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(spinner_target.getSelectedItemPosition() == 2 && Float.parseFloat(desired_weight.getText().toString()) < Float.parseFloat(weight.getText().toString())){
                    Toast.makeText(getContext(),"При наборе желаемый вес должен быть больше текщего",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner_target.getSelectedItemPosition() == 0 && Float.parseFloat(desired_weight.getText().toString()) > Float.parseFloat(weight.getText().toString())){
                    Toast.makeText(getContext(),"При похудении желаемый вес должен быть меньше текущего",Toast.LENGTH_SHORT).show();
                    return;
                }

                age_v = Integer.parseInt(age.getText().toString());
                height_v = Integer.parseInt(height.getText().toString());

                SaveAutoSettings(Float.parseFloat(weight.getText().toString()), Float.parseFloat(desired_weight.getText().toString()));
            }
        });
        return root;
    }

    private void FindView(View root) {

        formula_info = root.findViewById(R.id.form);
     tabLayout = root.findViewById(R.id.tab);
     weight = root.findViewById(R.id.this_weight);
     height = root.findViewById(R.id.height);
     age = root.findViewById(R.id.age);
     desired_weight = root.findViewById(R.id.desired_weight);
     harrison_formula = root.findViewById(R.id.harrison_formula);
     muffin_formula = root.findViewById(R.id.miffin_formula);
     male = root.findViewById(R.id.male);
     female = root.findViewById(R.id.female);
     spinner = root.findViewById(R.id.spinner);
     spinner_target = root.findViewById(R.id.spinner_target);
     to_calculate = root.findViewById(R.id.to_calculate);
     save = root.findViewById(R.id.save);
     conclusion = root.findViewById(R.id.conclusion);
    }

    @SuppressLint("DefaultLocale")
    public void SaveAutoSettings(float weight, float desired_weight){
        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        users = db.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/MyTarget");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        WeightV weightV = new WeightV();
        weightV.setChange(String.valueOf(0));
        weightV.setDate(calendar.getTimeInMillis());
        weightV.setWeight(String.valueOf(weight));

            try {
                sdb.execSQL("INSERT INTO " + myWeight.TABLE + " VALUES ('" + mAuth.getCurrentUser().getUid() + "','" +
                        calendar.getTimeInMillis() + "','" +
                        weight + "','" + 0 + "','" +
                        dateFormat.format(calendar.getTimeInMillis()) +
                        "');");
                CollectionReference colRef = fdb.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
                colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weightV);

            } catch (SQLiteConstraintException e) {
            }



        float watter = (float) (weight * 0.035);
        int protein = calorie / 4 / 4;
        int fat = calorie / 4 / 9;
        int carbohydrate = calorie / 2 / 4;

        SettingsCalculator settingsCalculator = new SettingsCalculator();

        settingsCalculator.setCalorie(calorie);
        settingsCalculator.setFormula(formula);
        settingsCalculator.setCurrent_weight(weight);
        settingsCalculator.setTarget(target);
        settingsCalculator.setHeight(height_v);
        settingsCalculator.setAge(age_v);
        settingsCalculator.setActivity(pos);
        settingsCalculator.setCarbohydrate(carbohydrate);
        settingsCalculator.setDesired_weight(desired_weight);
        settingsCalculator.setFat(fat);
        settingsCalculator.setProtein(protein);
        settingsCalculator.setWatter(watter);
        settingsCalculator.setSex(sex);
        settingsCalculator.setWeight(weight);
        settingsCalculator.setVar("Auto");

        users.setValue(settingsCalculator).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.nav_calculator_main);
            }
        });
    }

}