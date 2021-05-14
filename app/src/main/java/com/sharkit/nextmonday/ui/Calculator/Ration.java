package com.sharkit.nextmonday.ui.Calculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.Adapters.RationExpList;
import com.sharkit.nextmonday.MySQL.DataBasePFC;
import com.sharkit.nextmonday.MySQL.LinkRation;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.MealData;
import com.sharkit.nextmonday.variables.UserMeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Ration extends Fragment {
    ExpandableListView expandableListView;
    TextView date;
    ExpandableListAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/Meal");


    ArrayList<DataPFC> allNutrition;
    Button add;

    final String TAG = "qwerty";
    long num;

    ArrayList<String> count;
    ArrayList<ArrayList<UserMeal>> group;
    ArrayList<UserMeal> child;
    CollectionReference colRef, docRef, docRef1;

    DataBasePFC dataBasePFC;
    LinkRation linkRation;
    SQLiteDatabase sdl, sdd;
    Cursor query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_ration, container, false);
        FindView(root);

        dataBasePFC = new DataBasePFC(getApplicationContext());
        linkRation = new LinkRation(getApplicationContext());
        sdd = dataBasePFC.getReadableDatabase();
        sdl = linkRation.getReadableDatabase();
        linkRation.onCreate(sdl);

        SynchronizedRation();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTimeInMillis(DayOfWeek.getMillis());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        date.setText(dateFormat.format(DayOfWeek.getMillis()));

        if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) &&
                calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH)){
            ReturnNumber();
        }else{
            Number();
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View select = inflater.inflate(R.layout.calculator_alert_add_meal, null);

                EditText new_meal = select.findViewById(R.id.meal);

                dialog.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(new_meal.getText())) {
                            Toast.makeText(getActivity(), "error text", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        users.child(ReturnNumber()+"".trim()).setValue(new_meal.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(),"success", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                navController.navigate(R.id.nav_cal_ration);
                            }
                        });
                    }
                });
                dialog.setView(select);
                dialog.show();
            }
        });



        return root;
    }




    private void AllNutrition(String s) {
        s = "сніданок";
        sdl = linkRation.getReadableDatabase();

        allNutrition = new ArrayList<>();

        query = sdl.rawQuery("SELECT * FROM " + linkRation.TABLE + " WHERE " + linkRation.COLUMN_ID + " = '" +
                mAuth.getCurrentUser().getUid() + "' AND " + linkRation.COLUMN_MEAL + " = '" + s + "'",null);

        while (query.moveToNext()){
//            allNutrition.add(FindFoodBySQLFromBAR(query.getString(2), s));
//            Log.d(TAG, (FindFoodBySQLFromBAR(query.getString(2), s).getCalorie())+"");

        }
//        float a = Float.parseFloat(allNutrition.get(1).getCalorie());
//        Log.d(TAG, a +"");
    }

    public DataPFC FindFoodBySQLFromBAR(String code, String k){
        DataBasePFC dataBasePFC = new DataBasePFC(getApplicationContext());
        sdl = dataBasePFC.getReadableDatabase();

        DataPFC dataPFC = new DataPFC();
        query = sdl.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" +
                mAuth.getCurrentUser().getUid() + "' AND " + dataBasePFC.COLUMN_BAR_CODE + " = '" + code + "'",null);

        while (query.moveToNext()){
            WriteFromDATA_PFC(dataPFC, query);
        }
        return dataPFC;
    }

    private void WriteFromDATA_PFC(DataPFC dataPFC, Cursor query) {
        dataPFC.setName(query.getString(1));
        dataPFC.setID(mAuth.getCurrentUser().getUid());
        dataPFC.setCalorie(query.getString(3));
        dataPFC.setPortion(query.getString(2));
        dataPFC.setFat(query.getString(12));
        dataPFC.setSaturated_fat(query.getString(13));
        dataPFC.setTrans_fat(query.getString(14));
        dataPFC.setOmega_9(query.getString(15));
        dataPFC.setOmega_6(query.getString(16));
        dataPFC.setOmega_3(query.getString(17));
        dataPFC.setAla(query.getString(18));
        dataPFC.setDha(query.getString(19));
        dataPFC.setEpa(query.getString(20));
        dataPFC.setCarbohydrate(query.getString(9));
        dataPFC.setSimple_carbohydrates(query.getString(11));
        dataPFC.setComplex_carbohydrate(query.getString(10));
        dataPFC.setProtein(query.getString(4));
        dataPFC.setCasein_protein(query.getString(7));
        dataPFC.setAgg_protein(query.getString(8));
        dataPFC.setSoy_protein(query.getString(6));
        dataPFC.setWhey_protein(query.getString(5));
        dataPFC.setCellulose(query.getString(21));
        dataPFC.setWatter(query.getString(23));
        dataPFC.setSalt(query.getString(22));
        dataPFC.setCalcium(query.getString(24));
        dataPFC.setPotassium(query.getString(25));
        dataPFC.setBar_code(query.getString(26));
    }


    public long ReturnNumber(){

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                group = new ArrayList<>();
                num = snapshot.getChildrenCount();
                MealList(snapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return num ++;
    }

    public void Number(){
        group = new ArrayList<>();
        count = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() +
                "/MealInfo");
        colRef.document(dateFormat.format(DayOfWeek.getMillis())).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {

                    for (int i = 0; i < documentSnapshot.getData().size(); i++) {
                        count.add(documentSnapshot.get(i + "".trim()).toString());
                        FoodListFromSQLite(documentSnapshot.get(i + "").toString());
                    }
                }catch (NullPointerException e){}
            }
        });

}



    public void MealList(long a) {

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Map<String, Object> map = new HashMap<>();
        count = new ArrayList<>();
        for (long i = 0; i < a; i++) {
            long finalI = i;
            users.child(i+"").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {

                        colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MealInfo");
                        String k = snapshot.getValue(String.class);
                        map.put(finalI +"", k);
                        count.add(k);

                        colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(map);


                            FoodListFromSQLite(k);


                    } catch (NullPointerException e) {
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

    private void FoodListFromSQLite(String k) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        sdl = linkRation.getReadableDatabase();
        query = sdl.rawQuery("SELECT * FROM " + linkRation.TABLE + " WHERE " + linkRation.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() +
                "' AND " + linkRation.COLUMN_DATE + " = '" + dateFormat.format(DayOfWeek.getMillis()) + "' AND " +
                linkRation.COLUMN_MEAL + " = '" + k + "'", null);
        child = new ArrayList<>();
        while (query.moveToNext()){
            UserMeal userMeal = new UserMeal();
            WriteListDATA(userMeal);
            child.add(userMeal);
        }
        group.add(child);
        adapter = new RationExpList(count, group, getContext());
        expandableListView.setAdapter(adapter);
    }

    private void WriteListDATA(UserMeal userMeal) {

        userMeal.setDate(query.getString(1));
        userMeal.setMeal(query.getString(3));
        userMeal.setNumber(query.getInt(4));
        userMeal.setName(query.getString(5));
        userMeal.setPortion(query.getString(6));
        userMeal.setCalorie(query.getString(7));
        userMeal.setProtein(query.getString(8));
        userMeal.setWhey_protein(query.getString(9));
        userMeal.setSoy_protein(query.getString(10));
        userMeal.setCasein_protein(query.getString(11));
        userMeal.setAgg_protein(query.getString(12));
        userMeal.setCarbohydrate(query.getString(13));
        userMeal.setComplex_carbohydrate(query.getString(14));
        userMeal.setSimple_carbohydrates(query.getString(15));
        userMeal.setFat(query.getString(16));
        userMeal.setSaturated_fat(query.getString(17));
        userMeal.setTrans_fat(query.getString(18));
        userMeal.setOmega_9(query.getString(19));
        userMeal.setOmega_6(query.getString(20));
        userMeal.setOmega_3(query.getString(21));
        userMeal.setAla(query.getString(22));
        userMeal.setDha(query.getString(23));
        userMeal.setEpa(query.getString(24));
        userMeal.setCellulose(query.getString(25));
        userMeal.setSalt(query.getString(26));
        userMeal.setWatter(query.getString(27));
        userMeal.setCalcium(query.getString(28));
        userMeal.setPotassium(query.getString(29));
        userMeal.setDate_millis(query.getLong(30));

    }

    private void SynchronizedRation() {
        sdl = linkRation.getReadableDatabase();
        sdd = dataBasePFC.getReadableDatabase();
        docRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/Meal");
        docRef1 = db.collection("DB Product/");
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    MealData mealData = queryDocumentSnapshot.toObject(MealData.class);

                    docRef1.whereEqualTo("id", mAuth.getCurrentUser().getUid())
                            .whereEqualTo("bar_code", mealData.getCode())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot1 : queryDocumentSnapshots) {
                                DataPFC dataPFC = queryDocumentSnapshot1.toObject(DataPFC.class);

                                try {

                                    sdl.execSQL("INSERT INTO " + linkRation.TABLE + " VALUES ('" +
                                            mAuth.getCurrentUser().getUid() + "','" +
                                            mealData.getDate() + "','" +
                                            mealData.getCode() + "','" +
                                            mealData.getMeal() + "','" +
                                            mealData.getNumber() + "','" +
                                            dataPFC.getName() + "','" +
                                            dataPFC.getPortion() + "','" +
                                            dataPFC.getCalorie() + "','" +
                                            dataPFC.getProtein() + "','" +
                                            dataPFC.getWhey_protein() + "','" +
                                            dataPFC.getSoy_protein() + "','" +
                                            dataPFC.getCasein_protein() + "','" +
                                            dataPFC.getAgg_protein() + "','" +
                                            dataPFC.getCarbohydrate() + "','" +
                                            dataPFC.getComplex_carbohydrate() + "','" +
                                            dataPFC.getSimple_carbohydrates() + "','" +
                                            dataPFC.getFat() + "','" +
                                            dataPFC.getSaturated_fat() + "','" +
                                            dataPFC.getTrans_fat() + "','" +
                                            dataPFC.getOmega_9() + "','" +
                                            dataPFC.getOmega_6() + "','" +
                                            dataPFC.getOmega_3() + "','" +
                                            dataPFC.getAla() + "','" +
                                            dataPFC.getDha() + "','" +
                                            dataPFC.getEpa() + "','" +
                                            dataPFC.getCellulose() + "','" +
                                            dataPFC.getSalt() + "','" +
                                            dataPFC.getWatter() + "','" +
                                            dataPFC.getCalcium() + "','" +
                                            dataPFC.getPotassium() + "','" +
                                            mealData.getDate_millis() + "');");

                                } catch (SQLiteConstraintException e) {
                                }
                            }
                        }

                    });


//                        query = sdd.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" +
//                                mAuth.getCurrentUser().getUid() + "' AND " + dataBasePFC.COLUMN_BAR_CODE + " = '" + mealData.getCode() + "'",null);
//
//                        while (query.moveToNext()){
//
//                            try {
//

//
//                        }

                }
            }
        });
    }


    private void FindView(View root) {
        expandableListView = root.findViewById(R.id.ration);
        date = root.findViewById(R.id.date);
        add = root.findViewById(R.id.add_meal);

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}