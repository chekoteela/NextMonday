package com.sharkit.nextmonday.ui.Calculator;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.MySQL.DataBasePFC;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.PFC_today;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.RelativeLayout.ABOVE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ChangeFood extends Fragment {
    EditText potassium, salt, calcium, cellulose, watter, casein_protein, agg_protein, soy_protein, whey_protein,
    protein, complex_carbohydrate, simple_carbohydrate, carbohydrate, epa, dha, ala,
    omega3, omega6, omega9, trans_fat, saturated_fat, fat, name, portion, calorie;

    TextView text,text_p, text_c, text_f, text_d;
    LinearLayout change_food_linear;

    Button save_change;
    boolean success = true;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String id;
    DataBasePFC dataBasePFC;
    SQLiteDatabase db;
    Cursor query;
    ArrayList<String> tags;
    ScrollView scroll;
    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = fdb.collection("DB for moderation");
    CollectionReference collectionAdmin = fdb.collection("DB Product");

    float a, b, c, d, e, f, g, h;

    final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_change_food, container, false);


        FindView(root);

        dataBasePFC = new DataBasePFC(getApplicationContext());
        db = dataBasePFC.getReadableDatabase();
        dataBasePFC.onCreate(db);

        Adaptive();
        WriteChange();


        save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = name.getText().toString();
                if (TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(getContext(), "Введите имя продукта", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(portion.getText().toString())){
                    Toast.makeText(getContext(), "Введите порцию", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isProteinWeight()){
                    Toast.makeText(getContext(), "Введите корректное количество белков", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFatWeight()){
                    Toast.makeText(getContext(), "Введите корректное количество жиров", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isCarbohydrateWeight()){
                    Toast.makeText(getContext(), "Введите корректное количество углеводов", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ValidatorName(name.getText().toString())){
                    Toast.makeText(getContext(), "Имя не должно иметь символов", Toast.LENGTH_SHORT).show();
                    return;
                }
                EmptyEdit();
                if (PFC_today.getBar_code() == null) {
                    PFC_today.setBar_code(UUID.randomUUID().toString());
                    generateKey(inputText);
                    SQLiteWrite();
                    return;
                }
                generateKey(inputText);
            }
        });
        return root;
    }

    private boolean ValidatorName(String toString) {
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>`'?{}\\[\\]~]\"");
        Matcher hasSign = sign.matcher(toString);
        return hasSign.find();
    }

    private void Adaptive() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;
//встановлення параметрів верхнього Linear Layout з елементами включно
        LinearLayout.LayoutParams top_linear_params = new LinearLayout.LayoutParams(-1, h/16);
        top_linear_params.setMargins(h/42,h/84,h/42,h/84);
        name.setLayoutParams(top_linear_params);
        portion.setLayoutParams(top_linear_params);
        calorie.setLayoutParams(top_linear_params);
//
        //Встановлення параметрів Scroll View
        RelativeLayout.LayoutParams scroll_param = new RelativeLayout.LayoutParams(-1,(int)(h / 1.5));
        scroll_param.addRule(3, R.id.change_food_linear);
        scroll_param.addRule(2,R.id.save_change);
        scroll.setLayoutParams(scroll_param);



        LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(-1, h/16);
        parent_params.setMargins(h/42,h/84,h/42,h/84);

        LinearLayout.LayoutParams dot_params = new LinearLayout.LayoutParams(-1,h/16);
        dot_params.setMargins(h/21,h/84,h/42,h/84);

        LinearLayout.LayoutParams f_params = new LinearLayout.LayoutParams(-1, h/16);
        f_params.setMargins(h/14, h/84,h/42,h/84);

        RelativeLayout.LayoutParams but_params = new RelativeLayout.LayoutParams(-1, h/16);
        but_params.addRule(2, R.id.bar);
        but_params.setMargins(h/21,h/84, h/21, h/84);
        save_change.setPadding(0,0,0,0);
        save_change.setLayoutParams(but_params);





        cellulose.setLayoutParams(parent_params);
        watter.setLayoutParams(parent_params);
        salt.setLayoutParams(parent_params);
        potassium.setLayoutParams(parent_params);
        calcium.setLayoutParams(parent_params);


        protein.setLayoutParams(parent_params);
        whey_protein.setLayoutParams(dot_params);
        casein_protein.setLayoutParams(dot_params);
        soy_protein.setLayoutParams(dot_params);
        agg_protein.setLayoutParams(dot_params);

        carbohydrate.setLayoutParams(parent_params);
        simple_carbohydrate.setLayoutParams(dot_params);
        complex_carbohydrate.setLayoutParams(dot_params);

        fat.setLayoutParams(parent_params);
        trans_fat.setLayoutParams(dot_params);
        saturated_fat.setLayoutParams(dot_params);
        omega9.setLayoutParams(dot_params);
        omega6.setLayoutParams(dot_params);
        omega3.setLayoutParams(dot_params);
        ala.setLayoutParams(f_params);
        dha.setLayoutParams(f_params);
        epa.setLayoutParams(f_params);



        if (h < 1400){
            text_d.setTextSize(16);
            text_f.setTextSize(16);
            text_p.setTextSize(16);
            text_c.setTextSize(16);
            save_change.setTextSize(16);
        }



    }

    private void EmptyEdit() {
        if (TextUtils.isEmpty(watter.getText())){
            watter.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(cellulose.getText())){
            cellulose.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(potassium.getText())){
            potassium.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(salt.getText())){
            salt.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(calcium.getText())){
            calcium.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(casein_protein.getText())){
            casein_protein.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(agg_protein.getText())){
            agg_protein.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(soy_protein.getText())){
            soy_protein.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(whey_protein.getText())){
            whey_protein.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(protein.getText())){
            protein.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(complex_carbohydrate.getText())){
            complex_carbohydrate.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(simple_carbohydrate.getText())){
            simple_carbohydrate.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(carbohydrate.getText())){
            carbohydrate.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(epa.getText())){
            epa.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(dha.getText())){
            dha.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(ala.getText())){
            ala.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(omega3.getText())){
            omega3.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(omega6.getText())){
            omega6.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(omega9.getText())){
            omega9.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(saturated_fat.getText())){
            saturated_fat.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(trans_fat.getText())){
            trans_fat.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(fat.getText())){
            fat.setText(String.valueOf(0));}
        if (TextUtils.isEmpty(calorie.getText())){
            calorie.setText(String.valueOf(0));}
    }


    private void generateKey(String inputText) {
        String inputString = inputText.toLowerCase();
        String [] tagArray = inputString.split(" ");
        tags = new ArrayList<>();



        for (String word : tagArray){
            String a = "";
            char [] b = inputString.toCharArray();

            for (int i = 0; i < b.length; i++){
                a += b[i];
                tags.add(a);
            }
            inputString = inputString.replace(word, "").trim();
        }

        WriteToFireStore();

    }

    @SuppressLint("SetTextI18n")
    private boolean isCarbohydrateWeight() {
        if (!TextUtils.isEmpty(simple_carbohydrate.getText())){
            a = Float.parseFloat(simple_carbohydrate.getText().toString());
        }else {
            a = 0;
        }
        if (!TextUtils.isEmpty(complex_carbohydrate.getText())){
            b = Float.parseFloat(complex_carbohydrate.getText().toString());
        }else {
            b = 0;
        }
        if (TextUtils.isEmpty(carbohydrate.getText())){
            carbohydrate.setText(a + b + "");
        }
        return !(a + b <= Float.parseFloat(carbohydrate.getText().toString()));
    }

    @SuppressLint("SetTextI18n")
    private boolean isFatWeight() {
        if(!TextUtils.isEmpty(saturated_fat.getText())){
            a = Float.parseFloat(saturated_fat.getText().toString());
        }else {a = 0;}
        if (!TextUtils.isEmpty(trans_fat.getText())){
            b = Float.parseFloat(trans_fat.getText().toString());
        }else {b = 0;}
        if (!TextUtils.isEmpty(omega9.getText())){
            c = Float.parseFloat(omega9.getText().toString());
        }else {c = 0;}
        if (!TextUtils.isEmpty(omega6.getText())){
            d = Float.parseFloat(omega6.getText().toString());
        }else {d = 0;}
        if (!TextUtils.isEmpty(omega3.getText())){
            e = Float.parseFloat(omega3.getText().toString());
        }else {e = 0;}
        if (!TextUtils.isEmpty(ala.getText())){
            f = Float.parseFloat(ala.getText().toString());
        }else {f = 0;}
        if (!TextUtils.isEmpty(dha.getText())){
            g = Float.parseFloat(dha.getText().toString());
        }else {g = 0;}
        if (!TextUtils.isEmpty(epa.getText())){
            h = Float.parseFloat(epa.getText().toString());
        }else {h = 0;}
        if (TextUtils.isEmpty(fat.getText())){
            fat.setText(a + b + "");
        }

        return !(a + b  <= Float.parseFloat(fat.getText().toString()));
    }

    @SuppressLint("SetTextI18n")
    private boolean isProteinWeight() {
        if (!TextUtils.isEmpty(whey_protein.getText())){
            a = Float.parseFloat(whey_protein.getText().toString());
        }else{
            a = 0;
        }
        if (!TextUtils.isEmpty(casein_protein.getText())){
            b = Float.parseFloat(casein_protein.getText().toString());
        }else{
            b = 0;
        }
        if (!TextUtils.isEmpty(agg_protein.getText())){
            c = Float.parseFloat(agg_protein.getText().toString());
        }else{
            c = 0;
        }
        if (!TextUtils.isEmpty(soy_protein.getText())){
            d = Float.parseFloat(soy_protein.getText().toString());
        }else {
            d = 0;
        }
        if (TextUtils.isEmpty(protein.getText())){
            protein.setText(a + b + c + d + "");
        }
        return !(a + b + c + d <= Float.parseFloat(protein.getText().toString()));

    }

    private void WriteChange() {
        potassium.setText(LocalDataPFC.getPotassium());
        salt.setText(LocalDataPFC.getSalt());
        calcium.setText(LocalDataPFC.getCalcium());
        cellulose.setText(LocalDataPFC.getCellulose());
        watter.setText(LocalDataPFC.getWatter());
        casein_protein.setText(LocalDataPFC.getCasein_protein());
        agg_protein.setText(LocalDataPFC.getAgg_protein());
        soy_protein.setText(LocalDataPFC.getSoy_protein());
        whey_protein.setText(LocalDataPFC.getWhey_protein());
        protein.setText(LocalDataPFC.getProtein());
        complex_carbohydrate.setText(LocalDataPFC.getComplex_carbohydrate());
        simple_carbohydrate.setText(LocalDataPFC.getSimple_carbohydrates());
        carbohydrate.setText(LocalDataPFC.getCarbohydrate());
        epa.setText(LocalDataPFC.getEpa());
        dha.setText(LocalDataPFC.getDha());
        ala.setText(LocalDataPFC.getAla());
        omega3.setText(LocalDataPFC.getOmega_3());
        omega6.setText(LocalDataPFC.getOmega_6());
        omega9.setText(LocalDataPFC.getOmega_9());
        trans_fat.setText(LocalDataPFC.getTrans_fat());
        saturated_fat.setText(LocalDataPFC.getSaturated_fat());
        fat.setText(LocalDataPFC.getFat());
        calorie.setText(LocalDataPFC.getCalorie());
        portion.setText(LocalDataPFC.getPortion());
        name.setText(LocalDataPFC.getName());
    }

    private void WriteToFireStore() {

        DataPFC dataPFC = new DataPFC();
        WriteClass(dataPFC, PFC_today.getBar_code());

        try {

        if(Target.getModerator().equals("Moderator")) {

            collectionAdmin
                    .whereEqualTo("bar_code", PFC_today.getBar_code())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                            if (value.isEmpty()) {


                                collectionAdmin.add(dataPFC).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getContext(), "Успешное добавление продукта", Toast.LENGTH_SHORT).show();
                                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                        navController.navigate(R.id.nav_calculator_main);
                                    }
                                });

                            } else {
                                UpDateFireStore("DB Product");
                            }
                        }
                    });
        }

        }catch (NullPointerException e){}

            if (Target.getModerator() == null) {
                if (!isExistSQLite(PFC_today.getBar_code())) {
                    SQLiteUpdate(PFC_today.getBar_code());
                    UpDateFireStore("DB for moderation");
                } else {
                    SQLiteWrite();
                    collectionReference.add(dataPFC).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), "Успешное добавление продукта модерацию", Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.nav_calculator_main);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                    });
                }
            }
    }

    private void WriteClass(DataPFC dataPFC, String code) {
        FirebaseAuth mAuh = FirebaseAuth.getInstance();
        dataPFC.setID(mAuh.getCurrentUser().getUid());
        dataPFC.setName(name.getText().toString());
        dataPFC.setTag(tags);
        dataPFC.setBar_code(code);
        dataPFC.setCalorie(calorie.getText().toString());
        dataPFC.setPortion(portion.getText().toString());
        dataPFC.setFat(fat.getText().toString());
        dataPFC.setSaturated_fat(saturated_fat.getText().toString());
        dataPFC.setTrans_fat(trans_fat.getText().toString());
        dataPFC.setOmega_9(omega9.getText().toString());
        dataPFC.setOmega_6(omega6.getText().toString());
        dataPFC.setOmega_3(omega3.getText().toString());
        dataPFC.setAla(ala.getText().toString());
        dataPFC.setDha(dha.getText().toString());
        dataPFC.setEpa(epa.getText().toString());
        dataPFC.setCarbohydrate(carbohydrate.getText().toString());
        dataPFC.setSimple_carbohydrates(simple_carbohydrate.getText().toString());
        dataPFC.setComplex_carbohydrate(complex_carbohydrate.getText().toString());
        dataPFC.setProtein(protein.getText().toString());
        dataPFC.setCasein_protein(casein_protein.getText().toString());
        dataPFC.setAgg_protein(agg_protein.getText().toString());
        dataPFC.setSoy_protein(soy_protein.getText().toString());
        dataPFC.setWhey_protein(whey_protein.getText().toString());
        dataPFC.setCellulose(cellulose.getText().toString());
        dataPFC.setWatter(watter.getText().toString());
        dataPFC.setSalt(salt.getText().toString());
        dataPFC.setCalcium(calcium.getText().toString());
        dataPFC.setPotassium(potassium.getText().toString());
    }

    private void UpDateFireStore(String s) {


            fdb.collection(s)
                    .whereEqualTo("bar_code", PFC_today.getBar_code())
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                        id = queryDocumentSnapshot.getId();
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    try {
                    DataPFC dataPFC = new DataPFC();
                    WriteClass(dataPFC, PFC_today.getBar_code());
                    fdb.collection(s)
                            .document(id)
                            .set(dataPFC);
                    Toast.makeText(getContext(), "Обновленияя отправлены на модерацию", Toast.LENGTH_SHORT).show();
                }catch (NullPointerException e){
                    DataPFC dataPFC = new DataPFC();
                    WriteClass(dataPFC, PFC_today.getBar_code());
                    fdb.collection(s).add(dataPFC).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    });
                }
                }
            });

    }

    private void SQLiteUpdate(String code) {
        db = dataBasePFC.getReadableDatabase();
        db.execSQL("UPDATE " + dataBasePFC.TABLE + " SET " +
                dataBasePFC.COLUMN_NAME + " = '" + name.getText().toString() + "'," +
                dataBasePFC.COLUMN_PORTION + " = '" + portion.getText().toString() + "'," +
                dataBasePFC.COLUMN_CALORIE + " = '" + calorie.getText().toString() + "'," +
                dataBasePFC.COLUMN_PROTEIN + " = '" + protein.getText().toString() + "'," +
                dataBasePFC.COLUMN_WHEY_PROTEIN + " = '" + whey_protein.getText().toString() + "'," +
                dataBasePFC.COLUMN_SOY_PROTEIN + " = '" + soy_protein.getText().toString() + "'," +
                dataBasePFC.COLUMN_CASEIN_PROTEIN + " = '" + casein_protein.getText().toString() + "'," +
                dataBasePFC.COLUMN_AGG_PROTEIN + " = '" + agg_protein.getText().toString() + "'," +
                dataBasePFC.COLUMN_CARBOHYDRATE + " = '" + carbohydrate.getText().toString() + "'," +
                dataBasePFC.COLUMN_COMPLEX_C + " = '" + complex_carbohydrate.getText().toString() + "'," +
                dataBasePFC.COLUMN_SIMPLE_C + " = '" + simple_carbohydrate.getText().toString() + "'," +
                dataBasePFC.COLUMN_FAT + " = '" + fat.getText().toString() + "'," +
                dataBasePFC.COLUMN_SATURATED_FAT + " = '" + saturated_fat.getText().toString() + "'," +
                dataBasePFC.COLUMN_TRANS_FAT + " = '" + trans_fat.getText().toString() + "'," +
                dataBasePFC.COLUMN_OMEGA_9 + " = '" + omega9.getText().toString() + "'," +
                dataBasePFC.COLUMN_OMEGA_6 + " = '" + omega6.getText().toString() + "'," +
                dataBasePFC.COLUMN_OMEGA_3 + " = '" + omega3.getText().toString() + "'," +
                dataBasePFC.COLUMN_ALA + " = '" + ala.getText().toString() + "'," +
                dataBasePFC.COLUMN_DHA + " = '" + dha.getText().toString() + "'," +
                dataBasePFC.COLUMN_EPA + " = '" + epa.getText().toString() + "'," +
                dataBasePFC.COLUMN_CELLULOSE + " = '" + cellulose.getText().toString() + "'," +
                dataBasePFC.COLUMN_SALT + " = '" + salt.getText().toString() + "'," +
                dataBasePFC.COLUMN_WATTER + " = '" + watter.getText().toString() + "'," +
                dataBasePFC.COLUMN_CALCIUM + " = '" + calcium.getText().toString() + "'," +
                dataBasePFC.COLUMN_POTASSIUM + " = '" + potassium.getText().toString() + "' WHERE " +
                dataBasePFC.COLUMN_BAR_CODE + " = '" + code + "'");
    }

    private boolean isExistSQLite(String code) {
        db = dataBasePFC.getReadableDatabase();

            query = db.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "' AND "
                    + dataBasePFC.COLUMN_BAR_CODE + " = '" + code + "'", null);
            return (!query.moveToNext());


    }

    private void SQLiteWrite() {
        db = dataBasePFC.getReadableDatabase();
        mAuth = FirebaseAuth.getInstance();

        if (isExistSQLite(PFC_today.getBar_code())) {
            try {
                db.execSQL("INSERT INTO " + dataBasePFC.TABLE + " VALUES ('" + mAuth.getCurrentUser().getUid() + "','" +
                        name.getText().toString() + "','" +
                        portion.getText().toString() + "','" +
                        calorie.getText().toString() + "','" +
                        protein.getText().toString() + "','" +
                        whey_protein.getText().toString() + "','" +
                        soy_protein.getText().toString() + "','" +
                        casein_protein.getText().toString() + "','" +
                        agg_protein.getText().toString() + "','" +
                        carbohydrate.getText().toString() + "','" +
                        complex_carbohydrate.getText().toString() + "','" +
                        simple_carbohydrate.getText().toString() + "','" +
                        fat.getText().toString() + "','" +
                        saturated_fat.getText().toString() + "','" +
                        trans_fat.getText().toString() + "','" +
                        omega9.getText().toString() + "','" +
                        omega6.getText().toString() + "','" +
                        omega3.getText().toString() + "','" +
                        ala.getText().toString() + "','" +
                        dha.getText().toString() + "','" +
                        epa.getText().toString() + "','" +
                        cellulose.getText().toString() + "','" +
                        salt.getText().toString() + "','" +
                        watter.getText().toString() + "','" +
                        calcium.getText().toString() + "','" +
                        potassium.getText().toString() + "','" +
                        PFC_today.getBar_code() + "');");
            }catch (SQLiteException e){
                if(e.getCause() != null){
                    return;
                }
                Toast.makeText(getContext(), "syntax error", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void FindView(View root) {
       text_c = root.findViewById(R.id.text_carbohydrate);
       text_p = root.findViewById(R.id.text_protein);
       text_f = root.findViewById(R.id.text_fat);
       text_d = root.findViewById(R.id.text_els);

       save_change  = root.findViewById(R.id.save_change);

       potassium = root.findViewById(R.id.potassium);
       salt = root.findViewById(R.id.salt);
       calcium = root.findViewById(R.id.calcium);
       cellulose = root.findViewById(R.id.cellulose);
       watter = root.findViewById(R.id.watter);

       casein_protein = root.findViewById(R.id.casein_protein);
       agg_protein = root.findViewById(R.id.agg_protein);
       soy_protein = root.findViewById(R.id.soy_protein);
       whey_protein = root.findViewById(R.id.whey_protein);
       protein = root.findViewById(R.id.protein);

       complex_carbohydrate = root.findViewById(R.id.complex_carbohydrate);
       simple_carbohydrate = root.findViewById(R.id.simple_carbohydrates);
       carbohydrate = root.findViewById(R.id.carbohydrate);

       epa = root.findViewById(R.id.epa);
       dha = root.findViewById(R.id.dha);
       ala = root.findViewById(R.id.ala);
       omega3 = root.findViewById(R.id.omega3);
       omega6 = root.findViewById(R.id.omega6);
       omega9 = root.findViewById(R.id.omega9);
       trans_fat = root.findViewById(R.id.trans_fat);
       saturated_fat = root.findViewById(R.id.saturated_fat);
       fat = root.findViewById(R.id.fat);

       name = root.findViewById(R.id.name);
       portion = root.findViewById(R.id.portion);
       calorie = root.findViewById(R.id.calorie);

       text = root.findViewById(R.id.text);

       change_food_linear = root.findViewById(R.id.change_food_linear);
       scroll = root.findViewById(R.id.scrool);

    }
}