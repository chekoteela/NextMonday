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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Ration extends Fragment {
    ExpandableListView expandableListView;
    TextView date;
    ExpandableListAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/Meal");

    Calendar calendar = Calendar.getInstance();

    BottomNavigationView bar;


    ArrayList<DataPFC> allNutrition;
    Button adds;

    final String TAG = "qwerty";
    long num;

    Map<String, Object> data;
    ArrayList<Object> count;
    ArrayList<ArrayList<UserMeal>> group;
    ArrayList<UserMeal> child;
    CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MealInfo"),
          docRef, docRef1;
    DocumentReference documentReference;

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
        MenuItem menuItem = bar.getMenu().findItem(R.id.ration);

        menuItem.setIcon(R.drawable.meal_selected);

        SynchronizedRation();
        StartMeal();
        ListMeal();

        calendar.setTimeInMillis(DayOfWeek.getMillis());
        Calendar calendar1 = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        date.setText(dateFormat.format(DayOfWeek.getMillis()));




        if (calendar1.get(Calendar.YEAR) < calendar.get(Calendar.YEAR) ||
                calendar1.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH) ||
                calendar1.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)){
            // завтра
            ReturnToday();


        }else if (calendar1.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                calendar1.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)){
            //сьогодні
            ReturnToday();

        }else {
            //вчора
           Number();
        }



        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");



                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View select = inflater.inflate(R.layout.calculator_alert_add_meal, null);

                EditText new_meal = select.findViewById(R.id.meal);

                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(new_meal.getText())) {
                            Toast.makeText(getActivity(), "error text", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        count.add(new_meal.getText().toString().trim());

                        for (int i = 0; i < count.size(); i++){
                            data.put(String.valueOf(i), count.get(i));
                        }


                        colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(data);
                        users.setValue(count).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
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

    private void StartMeal() {
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void ListMeal(){
        data = new HashMap<>();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()){
                    count = new ArrayList<>();
                    count.add("Завтрак");
                    count.add("Перекус");
                    count.add("Обед");
                    count.add("Ужин");
                    users.setValue(count);
                    ExistMealToday();
                    return;
                }
                count = new ArrayList<>();
                    for (int i = 0; i < snapshot.getChildrenCount(); i++) {

                        int finalI = i;
                        users.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                String k = snapshot.getValue(String.class);
                                count.add(k);
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }



    private void ReturnToday() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        documentReference = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MealInfo")
                .document(dateFormat.format(calendar.getTimeInMillis()));
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()){ //список присутній
                    Number();
                }else {    // список відсутній
                    ExistMealToday();

                }
            }
        });

    }

    private void ExistMealToday() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for (int i = 0; i < count.size(); i++){
            data.put(String.valueOf(i), count.get(i));
        }
        colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(data);
        Log.d(TAG, "write to fs");
        ReturnToday();
    }


//    private void AllNutrition(String s) {
//        s = "сніданок";
//        sdl = linkRation.getReadableDatabase();
//
//        allNutrition = new ArrayList<>();
//
//        query = sdl.rawQuery("SELECT * FROM " + linkRation.TABLE + " WHERE " + linkRation.COLUMN_ID + " = '" +
//                mAuth.getCurrentUser().getUid() + "' AND " + linkRation.COLUMN_MEAL + " = '" + s + "'",null);
//
//        while (query.moveToNext()){
////            allNutrition.add(FindFoodBySQLFromBAR(query.getString(2), s));
////            Log.d(TAG, (FindFoodBySQLFromBAR(query.getString(2), s).getCalorie())+"");
//
//        }
//
//    }

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




    public void Number(){
        group = new ArrayList<>();
        count = new ArrayList<Object>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        colRef.document(dateFormat.format(DayOfWeek.getMillis())).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    for (int i = 0; i < documentSnapshot.getData().size(); i++) {
                        count.add(documentSnapshot.get(String.valueOf(i)));
                        FoodListFromSQLite(documentSnapshot.get(String.valueOf(i)));
                    }
                }catch (NullPointerException e){}
            }
        });
}

//    public void ReturnNumber(){
//
//                users.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        group = new ArrayList<>();
//                        num = snapshot.getChildrenCount();
//                        MealList(snapshot.getChildrenCount());
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//    public void MealList(long a) {
//
//        Calendar calendar = Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//        Map<String, Object> map = new HashMap<>();
//        count = new ArrayList<Object>();
//        for (long i = 0; i < a; i++) {
//            long finalI = i;
//            users.child(i+"").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    try {
//
//                        colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MealInfo");
//                        String k = snapshot.getValue(String.class);
//                        map.put(finalI +"", k);
//                        count.add(k);
//
//                       colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(map);
//
//                        Log.d(TAG, "mealList");
//                            FoodListFromSQLite(k);
//
//
//                    } catch (NullPointerException e) {
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                }
//            });
//        }
//    }

    private void FoodListFromSQLite(Object k) {

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

                    if (!queryDocumentSnapshot.exists()) {
                        Log.d(TAG, "exist");
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


                    } else {
                        Log.d(TAG, "dont exist");
                        query = sdd.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" +
                                mAuth.getCurrentUser().getUid() + "' AND " + dataBasePFC.COLUMN_BAR_CODE + " = '" + mealData.getCode() + "'", null);

                        while (query.moveToNext()) {
                            try {

                                sdl.execSQL("INSERT INTO " + linkRation.TABLE + " VALUES ('" +
                                        query.getString(0) + "','" +
                                        mealData.getDate() + "','" +
                                        mealData.getCode() + "','" +
                                        mealData.getMeal() + "','" +
                                        mealData.getNumber() + "','" +
                                        query.getString(1) + "','" +
                                        query.getString(2) + "','" +
                                        query.getString(3) + "','" +
                                        query.getString(4) + "','" +
                                        query.getString(5) + "','" +
                                        query.getString(6) + "','" +
                                        query.getString(7) + "','" +
                                        query.getString(8) + "','" +
                                        query.getString(9) + "','" +
                                        query.getString(10) + "','" +
                                        query.getString(11) + "','" +
                                        query.getString(12) + "','" +
                                        query.getString(13) + "','" +
                                        query.getString(14) + "','" +
                                        query.getString(15) + "','" +
                                        query.getString(16) + "','" +
                                        query.getString(17) + "','" +
                                        query.getString(18) + "','" +
                                        query.getString(19) + "','" +
                                        query.getString(20) + "','" +
                                        query.getString(21) + "','" +
                                        query.getString(22) + "','" +
                                        query.getString(23) + "','" +
                                        query.getString(24) + "','" +
                                        query.getString(25) + "','" +
                                        mealData.getDate_millis() + "');");

                            } catch (SQLiteConstraintException e) {
                            }
                        }
                    }
                }
            }
        });

    }


    private void FindView(View root) {
        expandableListView = root.findViewById(R.id.ration_xml);
        date = root.findViewById(R.id.date_xml);
        adds = root.findViewById(R.id.add_meal_xml);
        bar = root.findViewById(R.id.bar_xml);


    }


    @Override
    public void onStop() {
        super.onStop();

    }
}