package com.sharkit.nextmonday.ui.Calculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.progress.progressview.ProgressView;
import com.sharkit.nextmonday.MySQL.LinkRation;
import com.sharkit.nextmonday.MySQL.MyWeight;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.MealData;
import com.sharkit.nextmonday.variables.PFC_today;
import com.sharkit.nextmonday.variables.SettingsCalculator;
import com.sharkit.nextmonday.variables.UserMeal;
import com.sharkit.nextmonday.variables.WeightV;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Calculator_Main extends Fragment {
    ImageView add_food, add_watter, add_activity, add_weight;
    ProgressView calorie, protein, fat, carbohydrate, watter;
    TextView percent_calorie, percent_protein, percent_fat, percent_carbohydrate,percent_watter,
            eat_c, eat_calorie, all_calorie, eat_f, eat_fat, all_fat,
    eatC, eat_carbohydrate, all_carbohydrate, eat_p, eat_protein, all_protein,
    drink_w, drink_watter, all_watter;
    TapBarMenu tap_bar;

    int percent_w, percent_p, percent_ch, percent_c, percent_f;


    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    DatabaseReference users;
    LinkRation linkRation;
    SQLiteDatabase sdb;
    MyWeight myWeight;

    Cursor query;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference docRef;

    final String TAG = "qwerty";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_main, container, false);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        mAuth = FirebaseAuth.getInstance();
        FindViewByID(root);
        linkRation = new LinkRation(getApplicationContext());
        sdb = linkRation.getReadableDatabase();
        linkRation.onCreate(sdb);
        SumMealNutrition();
        SynchronizedPFC();

        add_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.calculator_weigth_button_dialog, null);
                EditText weight = existProduct.findViewById(R.id.weight);
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            WriteDataWeight(weight.getText().toString());
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });

        add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PFC_today.setPage("");
                navController.navigate(R.id.nav_cal_find_food_by_name);
            }
        });
        tap_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tap_bar.toggle();
            }
        });

        return root;
    }

    private void WriteDataWeight(String s) {
        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,17);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        query = sdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);

        query.moveToLast();

        WeightV weight = new WeightV();
        weight.setDate(calendar.getTimeInMillis());
        weight.setWeight(s);

        try {
            weight.setChange(String.format( Locale.ROOT,"%.1f",(Float.parseFloat(s) - Float.parseFloat(query.getString(2)))));
        }catch (CursorIndexOutOfBoundsException e){
            weight.setChange(String.valueOf(0));
        }



        try {
            sdb.execSQL("INSERT INTO " + myWeight.TABLE + " VALUES ('" + mAuth.getCurrentUser().getUid() + "','" +
                    calendar.getTimeInMillis() + "','" +
                    s + "','" + weight.getChange() + "');");

            CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
            colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weight);

        }catch (SQLiteConstraintException e){}

    }

    @SuppressLint("DefaultLocale")
    private void WriteText() {

        eat_c.setText(String.format("%.0f", PFC_today.getCalorie_eat()));
        eat_calorie.setText(String.format("%.0f", PFC_today.getCalorie_eat()));
        all_calorie.setText(String.format("%.0f", PFC_today.getCalorie()));

        eat_f.setText(String.format("%.1f", PFC_today.getFat_eat()));
        eat_fat.setText(String.format("%.1f", PFC_today.getFat_eat()));
        all_fat.setText(String.format("%.1f", PFC_today.getFat()));

        eatC.setText(String.format("%.1f", PFC_today.getCarbohydrate_eat()));
        eat_carbohydrate.setText(String.format("%.1f", PFC_today.getCarbohydrate_eat()));
        all_carbohydrate.setText(String.format("%.1f", PFC_today.getCarbohydrate()));

        eat_p.setText(String.format("%.1f", PFC_today.getProtein_eat()));
        eat_protein.setText(String.format("%.1f", PFC_today.getProtein_eat()));
        all_protein.setText(String.format("%.1f", PFC_today.getProtein()));

        drink_w.setText(String.valueOf(PFC_today.getWatter_drink()));
        drink_watter.setText(String.valueOf(PFC_today.getWatter_drink()));
        all_watter.setText(String.valueOf(PFC_today.getWatter()));

    }



    private void SynchronizedRation() {
        sdb = linkRation.getReadableDatabase();
        docRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/Meal");
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    MealData mealData = queryDocumentSnapshot.toObject(MealData.class);


                    try {
                        sdb.execSQL("INSERT INTO " + linkRation.TABLE + " VALUES ('" +
                                mAuth.getCurrentUser().getUid() + "','" +
                                mealData.getDate() + "','" +
                                mealData.getCode() + "','" +
                                mealData.getMeal() + "','" +
                                mealData.getNumber() + "','" +
                                mealData.getDate_millis() +
                                "');");
                    }catch (SQLiteConstraintException e){}
                }
            }
        });
    }

    private void ProgressMath() {

        percent_c = ProgressBar(PFC_today.getCalorie_eat(), PFC_today.getCalorie());
        percent_w = ProgressBar(PFC_today.getWatter_drink(), PFC_today.getWatter());
        percent_ch = ProgressBar(PFC_today.getCarbohydrate_eat(), PFC_today.getCarbohydrate());
        percent_f = ProgressBar(PFC_today.getFat_eat(), PFC_today.getFat());
        percent_p = ProgressBar(PFC_today.getProtein_eat(), PFC_today.getProtein());
        WriteText();
        ProgressBarDraw();
    }

    @SuppressLint("SetTextI18n")
    private void ProgressBarDraw() {
        percent_calorie.setText(percent_c + "%");
        percent_carbohydrate.setText(percent_ch + "%");
        percent_watter.setText(percent_w + "%");
        percent_fat.setText(percent_f + "%");
        percent_protein.setText(percent_p + "%");


        calorie.setProgress((float)percent_c / 100);
        carbohydrate.setProgress((float)percent_ch / 100);
        protein.setProgress((float)percent_p / 100);
        fat.setProgress((float)percent_f / 100);
        watter.setProgress((float)percent_w / 100);
    }

    public int ProgressBar(float before, float after) {

        int a = 0;
        if (before != 0) {
            a = (int) ((int) (100 * before) / after);
        }
        return a;
    }

    private void SynchronizedPFC() {
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/MyTarget");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SettingsCalculator settingsCalculator = snapshot.getValue(SettingsCalculator.class);
                try {
                    PFC_today.setCurrent_weight(String.valueOf(settingsCalculator.getDesired_weight()));
                    PFC_today.setWeight(String.valueOf(settingsCalculator.getWeight()));
                    PFC_today.setWatter(settingsCalculator.getWatter());
                    PFC_today.setProtein(settingsCalculator.getProtein());
                    PFC_today.setFat(settingsCalculator.getFat());
                    PFC_today.setCarbohydrate(settingsCalculator.getCarbohydrate());
                    PFC_today.setCalorie(settingsCalculator.getCalorie());
                }catch (NullPointerException e){
                    ProgressBarMathNull();

                }

                ProgressMath();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ProgressBarMathNull() {
        PFC_today.setCalorie(PFC_today.getCalorie_eat());
        PFC_today.setWatter(PFC_today.getWatter_drink());
        PFC_today.setProtein(PFC_today.getProtein_eat());
        PFC_today.setFat(PFC_today.getFat_eat());
        PFC_today.setCarbohydrate(PFC_today.getCarbohydrate_eat());

    }

    public void SumMealNutrition(){
        linkRation = new LinkRation(getApplicationContext());
        sdb = linkRation.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        UserMeal userMeal = new UserMeal();
        userMeal.setCalorie("0");
        userMeal.setWatter("0");
        userMeal.setCarbohydrate("0");
        userMeal.setProtein("0");
        userMeal.setFat("0");
        query = sdb.rawQuery("SELECT * FROM " + linkRation.TABLE + " WHERE " +
                linkRation.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "' AND " +
                linkRation.COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "'", null);
        while (query.moveToNext()){
            WritePFC_Today(userMeal);
        }

    }

    @SuppressLint("DefaultLocale")
    private void WritePFC_Today(UserMeal userMeal) {
        try {

            userMeal.setCalorie(String.valueOf(Float.parseFloat(userMeal.getCalorie()) + Float.parseFloat(query.getString(7)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));
            PFC_today.setCalorie_eat(Float.parseFloat(userMeal.getCalorie()));

            userMeal.setProtein(String.valueOf(Float.parseFloat(userMeal.getProtein()) + Float.parseFloat(query.getString(8)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));
            PFC_today.setProtein_eat(Float.parseFloat(userMeal.getProtein()));

            userMeal.setFat(String.valueOf(Float.parseFloat(userMeal.getFat()) + Float.parseFloat(query.getString(16)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));
            PFC_today.setFat_eat(Float.parseFloat(userMeal.getFat()));

            userMeal.setCarbohydrate(String.valueOf(Float.parseFloat(userMeal.getCarbohydrate()) + Float.parseFloat(query.getString(13)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));
            PFC_today.setCarbohydrate_eat(Float.parseFloat(userMeal.getCarbohydrate()));

            userMeal.setWatter(String.valueOf(Float.parseFloat(userMeal.getWatter()) + Float.parseFloat(query.getString(27)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4)) / 100));
            PFC_today.setWatter_drink(Float.parseFloat(userMeal.getWatter()));

        }catch (NumberFormatException e){
            Log.d(TAG, e.getLocalizedMessage());
        }

    }


    private void FindViewByID(View root) {
        tap_bar = root.findViewById(R.id.tapBarMenu);
        add_food = root.findViewById(R.id.add);
        add_weight = root.findViewById(R.id.weight);

        eat_c = root.findViewById(R.id.eat_c);
        eat_calorie = root.findViewById(R.id.eat_calorie);
        all_calorie = root.findViewById(R.id.all_calorie);
        eat_f = root.findViewById(R.id.eat_f);
        eat_fat = root.findViewById(R.id.eat_fat);
        all_fat = root.findViewById(R.id.all_fat);
        eatC = root.findViewById(R.id.eatC);
        eat_carbohydrate = root.findViewById(R.id.eat_carbohydrate);
        all_carbohydrate = root.findViewById(R.id.all_carbohydrate);
        eat_p = root.findViewById(R.id.eat_p);
        eat_protein = root.findViewById(R.id.eat_protein);
        all_protein = root.findViewById(R.id.all_protein);
        drink_w = root.findViewById(R.id.drink_w);
        drink_watter = root.findViewById(R.id.drink_watter);
        all_watter = root.findViewById(R.id.all_watter);

        calorie = root.findViewById(R.id.calorie);
        protein = root.findViewById(R.id.protein);
        fat = root.findViewById(R.id.fat);
        carbohydrate = root.findViewById(R.id.carbohydrate);
        watter = root.findViewById(R.id.watter);

        percent_calorie = root.findViewById(R.id.percent_calorie);
        percent_protein = root.findViewById(R.id.percent_protein);
        percent_fat = root.findViewById(R.id.percent_fat);
        percent_carbohydrate = root.findViewById(R.id.percent_carbohydrate);
        percent_watter = root.findViewById(R.id.percent_watter);
    }

    @Override
    public void onStop() {
        super.onStop();
        query.close();
        sdb.close();
    }
}