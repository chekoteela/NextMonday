package com.sharkit.nextmonday.ui.Calculator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.sharkit.nextmonday.Adapters.MyFindFoodAdaptor;
import com.sharkit.nextmonday.Configuration.CaptureAct;
import com.sharkit.nextmonday.MySQL.DataBasePFC;
import com.sharkit.nextmonday.MySQL.FavoriteFood;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.PFC_today;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Find_food_by_name extends Fragment {
    final String TAG = "qwerty";

    MyFindFoodAdaptor adaptor;

    ArrayList<DataPFC> group;
    ArrayList<String> child;

    ExpandableListView list_item;
    Button add;
    EditText find_food;



    DataBasePFC dataBasePFC;
    FavoriteFood favoriteFood;
    SQLiteDatabase fdb,ddb;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/FavoriteMeal");

    Cursor query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_find_food_by_name, container, false);
        NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

        FindView(root);
        Adaptive();


        TabLayout tabLayout = root.findViewById(R.id.tab_layout);

        dataBasePFC = new DataBasePFC(getApplicationContext());
        fdb = dataBasePFC.getReadableDatabase();
        dataBasePFC.onCreate(fdb);
        favoriteFood = new FavoriteFood(getApplicationContext());
        ddb = favoriteFood.getReadableDatabase();
        favoriteFood.onCreate(fdb);

        if (tabLayout.getSelectedTabPosition() == 0){
            SQLiteList();
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PFC_today.setBar_code(null);
                NullList();
                navController.navigate(R.id.nav_cal_change_food);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 2:
                        ScanCode();
                        break;
                    case 1:
                        FindMyListFood("123456789");
                        break;
                    case 0:
                        SQLiteList();
                        break;
                    case 3:
                        FindFavoriteFood();

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

        find_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 1:
                        if (count > 1) {
                            FindMyListFood(s.toString());
                        }
                        break;
                    case 0:
                        FindFromSQL(s);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return root;
    }

    private void Adaptive() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;

        RelativeLayout.LayoutParams but_params = new RelativeLayout.LayoutParams(-1, h/16);
        but_params.setMargins(h/21,h/84, h/21, h/84);
        but_params.addRule(2, R.id.bar);
        add.setLayoutParams(but_params);
        add.setPadding(0,0,0,0);

        LinearLayout.LayoutParams dot_params = new LinearLayout.LayoutParams(-1,h/16);
        dot_params.setMargins(h/42,h/11,h/42,h/84);
        find_food.setLayoutParams(dot_params);



        if (h < 1400){
           add.setTextSize(16);
        }
    }

    private void FindFavoriteFood() {
        group = new ArrayList<>();
        query = ddb.rawQuery("SELECT * FROM " + favoriteFood.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'",null);

        while (query.moveToNext()){
            DataPFC dataPFC = new DataPFC();
            WriteListDATA(dataPFC);
            group.add(dataPFC);
        }

        adaptor = new MyFindFoodAdaptor(getActivity(), getContext(), group);
        list_item.setAdapter(adaptor);
    }

    private void NullList() {
        LocalDataPFC.setName(null);
        LocalDataPFC.setPortion(null);
        LocalDataPFC.setCalorie(null);
        LocalDataPFC.setProtein(null);
        LocalDataPFC.setWhey_protein(null);
        LocalDataPFC.setSoy_protein(null);
        LocalDataPFC.setAgg_protein(null);
        LocalDataPFC.setCasein_protein(null);
        LocalDataPFC.setCarbohydrate(null);
        LocalDataPFC.setSimple_carbohydrates(null);
        LocalDataPFC.setComplex_carbohydrate(null);
        LocalDataPFC.setFat(null);
        LocalDataPFC.setSaturated_fat(null);
        LocalDataPFC.setTrans_fat(null);
        LocalDataPFC.setOmega_9(null);
        LocalDataPFC.setOmega_6(null);
        LocalDataPFC.setOmega_3(null);
        LocalDataPFC.setAla(null);
        LocalDataPFC.setDha(null);
        LocalDataPFC.setEpa(null);
        LocalDataPFC.setWatter(null);
        LocalDataPFC.setCellulose(null);
        LocalDataPFC.setSalt(null);
        LocalDataPFC.setCalcium(null);
        LocalDataPFC.setPotassium(null);
    }

    private void FindFromSQL(CharSequence s) {
        group = new ArrayList<>();
        query = fdb.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid()
                + "' AND " + dataBasePFC.COLUMN_NAME + " LIKE '" + s + "%'", null);
        while (query.moveToNext()){
            DataPFC dataPFC = new DataPFC();
            WriteListDATA(dataPFC);
            group.add(dataPFC);
        }
        adaptor = new MyFindFoodAdaptor(getActivity(),getContext(),group);
        list_item.setAdapter(adaptor);
    }

    private void SQLiteList() {

        group = new ArrayList<>();
        query = fdb.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'",null);

        while (query.moveToNext()){
            DataPFC dataPFC = new DataPFC();
            WriteListDATA(dataPFC);
            group.add(dataPFC);
        }

        adaptor = new MyFindFoodAdaptor(getActivity(), getContext(), group);
        list_item.setAdapter(adaptor);
    }

    private void WriteListDATA(DataPFC dataPFC) {
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


    private void FindMyListFood(String s) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("DB Product");
        group = new ArrayList<>();

            collRef
                    .whereArrayContains("tag", s)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        DataPFC dataPFC = documentSnapshot.toObject(DataPFC.class);
                        group.add(dataPFC);
                    }
                    adaptor = new MyFindFoodAdaptor(getActivity(), getContext(), group);

                    list_item.setAdapter(adaptor);
                }
            });
    }


    private void FindView(View root) {

        list_item = root.findViewById(R.id.list_item);
        add =root.findViewById(R.id.add);
        find_food = root.findViewById(R.id.find_food);

    }


    private void ScanCode() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Отсканируйте продукт который хотите добавить");
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.initiateScan();
    }

}