package com.sharkit.nextmonday.ui.settings;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.sharkit.nextmonday.variables.Manual_settings;
import com.sharkit.nextmonday.variables.WeightV;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Manual_calculate_calorie extends Fragment {
    TabLayout tabLayout;

    EditText weight, protein, carbohydrate, fat, watter;

    TextView conclusion;

    Button result, save;

    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference users = db.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/MyTarget");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculator_manual_calculate_calorie, container, false);

        FindView(root);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


        result.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(weight.getText())){
                    Toast.makeText(getContext(), "Введите ваш текущий вес", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(protein.getText())){
                    Toast.makeText(getContext(), "Введите количество белков на кг веса", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(carbohydrate.getText())){
                    Toast.makeText(getContext(), "Введите количество угливодов на кг веса", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fat.getText())){
                    Toast.makeText(getContext(), "Введите количество жиров на кг веса", Toast.LENGTH_SHORT).show();
                    return;
                }
                double res = (Float.parseFloat(weight.getText().toString()) * Float.parseFloat(protein.getText().toString()) * 4) +
                        (Float.parseFloat(weight.getText().toString()) * Float.parseFloat(carbohydrate.getText().toString()) * 4) +
                        (Float.parseFloat(weight.getText().toString()) * Float.parseFloat(fat.getText().toString()) * 9);
                conclusion.setText(String.format("%.0f", res));

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                MyWeight myWeight = new MyWeight(getApplicationContext());
                SQLiteDatabase sdb = myWeight.getReadableDatabase();
                myWeight.onCreate(sdb);

                Calendar calendar = Calendar.getInstance();
                if (TextUtils.isEmpty(weight.getText())){
                    Toast.makeText(getContext(), "Введите ваш текущий вес", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(protein.getText())){
                    Toast.makeText(getContext(), "Введите количество белков на кг веса", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(carbohydrate.getText())){
                    Toast.makeText(getContext(), "Введите количество угливодов на кг веса", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fat.getText())){
                    Toast.makeText(getContext(), "Введите количество жиров на кг веса", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(conclusion.getText())){
                    Toast.makeText(getContext(), "Рассчитайте калории", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(watter.getText().toString())){
                    watter.setText(String.valueOf(0));
                }

                WeightV weightV = new WeightV();
                weightV.setChange(String.valueOf(0));
                weightV.setDate(calendar.getTimeInMillis());
                weightV.setWeight(weight.getText().toString());
                try {
                    sdb.execSQL("INSERT INTO " + myWeight.TABLE + " VALUES ('" + mAuth.getCurrentUser().getUid() + "','" +
                            calendar.getTimeInMillis() + "','" +
                            weight.getText().toString() + "','" + 0 + "','" +
                            dateFormat.format(calendar.getTimeInMillis()) +
                            "');");
                    CollectionReference colRef = fdb.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
                    colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weightV);

                } catch (SQLiteConstraintException e) {
                }

                WriteToFB();
            }
        });



        TabLayout.Tab tab = tabLayout.getTabAt(1);
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


        return root;
    }

    private void WriteToFB() {
        Manual_settings manualSettings = new Manual_settings();

        manualSettings.setVar("Manual");
        manualSettings.setCarbohydrate(Integer.parseInt(carbohydrate.getText().toString()));
        manualSettings.setFat(Integer.parseInt(fat.getText().toString()));
        manualSettings.setProtein(Integer.parseInt(protein.getText().toString()));
        manualSettings.setWatter(Float.parseFloat(watter.getText().toString()));
        manualSettings.setWeight(Float.parseFloat(weight.getText().toString()));

        users.setValue(manualSettings).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_calculator_main);
            }
        });
    }

    private void FindView(View root) {
        tabLayout = root.findViewById(R.id.tab);
        weight = root.findViewById(R.id.weight);
        protein = root.findViewById(R.id.protein);
        carbohydrate = root.findViewById(R.id.carbohydrate);
        fat = root.findViewById(R.id.fat);
        watter = root.findViewById(R.id.watter);
        conclusion = root.findViewById(R.id.conclusion);
        result = root.findViewById(R.id.to_calculate);
        save = root.findViewById(R.id.save);
    }
}
