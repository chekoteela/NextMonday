package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.PFC_today;

import java.util.ArrayList;
import java.util.Locale;

public class MyFindFoodAdaptor extends BaseExpandableListAdapter {
    Activity mActivity;
    Context  mContext;
    ArrayList<DataPFC> mGroup;
    final String TAG = "qwerty";



    protected TextView potassium, salt, calcium, cellulose, watter, casein_protein, agg_protein, soy_protein, whey_protein,
            protein, complex_carbohydrate, simple_carbohydrate, carbohydrate, epa, dha, ala,
            omega3, omega6, omega9, trans_fat, saturated_fat, fat, name, calorie,
            potassium_text, salt_text, calcium_text, cellulose_text, watter_text, casein_protein_text,
            agg_protein_text, soy_protein_text, whey_protein_text, protein_text, complex_carbohydrate_text,
            simple_carbohydrate_text, carbohydrate_text, epa_text, dha_text, ala_text,
            omega3_text, omega6_text, omega9_text, trans_fat_text, saturated_fat_text, fat_text, calorie_text,
            weight, all_fat, all_carbohydrate, all_protein, all_calorie;

    protected LinearLayout up_linear;

    public MyFindFoodAdaptor(Activity mActivity, Context mContext, ArrayList<DataPFC> mGroup) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mGroup = mGroup;
    }

    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calculator_food_item_list, null);
        }
        FindViewParent(convertView);

        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
        name.setText(mGroup.get(groupPosition).getName());
        all_fat.setText("Жиры: " + mGroup.get(groupPosition).getFat());
        all_carbohydrate.setText("Углеводы: " + mGroup.get(groupPosition).getCarbohydrate());
        all_calorie.setText("Ккал: " + mGroup.get(groupPosition).getCalorie());
        all_protein.setText("Белки: " + mGroup.get(groupPosition).getProtein());
        weight.setText(mGroup.get(groupPosition).getPortion() + " грамм");

        up_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteListNutrition(groupPosition);
                navController.navigate(R.id.nav_cal_my_food);
            }
        });

        return convertView;
    }

    private void WriteListNutrition(int groupPosition) {
        LocalDataPFC.setName(mGroup.get(groupPosition).getName());
        LocalDataPFC.setPortion(mGroup.get(groupPosition).getPortion());
        LocalDataPFC.setCalorie(mGroup.get(groupPosition).getCalorie());
        LocalDataPFC.setProtein(mGroup.get(groupPosition).getProtein());
        LocalDataPFC.setWhey_protein(mGroup.get(groupPosition).getWhey_protein());
        LocalDataPFC.setSoy_protein(mGroup.get(groupPosition).getSoy_protein());
        LocalDataPFC.setAgg_protein(mGroup.get(groupPosition).getAgg_protein());
        LocalDataPFC.setCasein_protein(mGroup.get(groupPosition).getCasein_protein());
        LocalDataPFC.setCarbohydrate(mGroup.get(groupPosition).getCarbohydrate());
        LocalDataPFC.setSimple_carbohydrates(mGroup.get(groupPosition).getSimple_carbohydrates());
        LocalDataPFC.setComplex_carbohydrate(mGroup.get(groupPosition).getComplex_carbohydrate());
        LocalDataPFC.setFat(mGroup.get(groupPosition).getFat());
        LocalDataPFC.setSaturated_fat(mGroup.get(groupPosition).getSaturated_fat());
        LocalDataPFC.setTrans_fat(mGroup.get(groupPosition).getTrans_fat());
        LocalDataPFC.setOmega_9(mGroup.get(groupPosition).getOmega_9());
        LocalDataPFC.setOmega_6(mGroup.get(groupPosition).getOmega_6());
        LocalDataPFC.setOmega_3(mGroup.get(groupPosition).getOmega_3());
        LocalDataPFC.setAla(mGroup.get(groupPosition).getAla());
        LocalDataPFC.setDha(mGroup.get(groupPosition).getDha());
        LocalDataPFC.setEpa(mGroup.get(groupPosition).getEpa());
        LocalDataPFC.setWatter(mGroup.get(groupPosition).getWatter());
        LocalDataPFC.setCellulose(mGroup.get(groupPosition).getCellulose());
        LocalDataPFC.setSalt(mGroup.get(groupPosition).getSalt());
        LocalDataPFC.setCalcium(mGroup.get(groupPosition).getCalcium());
        LocalDataPFC.setPotassium(mGroup.get(groupPosition).getPotassium());
        PFC_today.setBar_code(mGroup.get(groupPosition).getBar_code());

    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calculator_child_info, null);
        }
        FindViewChild(convertView);
        WriteChild(groupPosition);
        ShowView();

        return convertView;
    }

    @SuppressLint("SetTextI18n")
    private void WriteChild(int groupPosition) {
        calorie.setText(mGroup.get(groupPosition).getCalorie() + " Ккал");
        protein.setText(mGroup.get(groupPosition).getProtein() + " гр");
        whey_protein.setText(mGroup.get(groupPosition).getWhey_protein() + " гр");
        soy_protein.setText(mGroup.get(groupPosition).getSoy_protein() + " гр");
        agg_protein.setText(mGroup.get(groupPosition).getAgg_protein() + " гр");
        casein_protein.setText(mGroup.get(groupPosition).getCasein_protein() + " гр");
        carbohydrate.setText(mGroup.get(groupPosition).getCarbohydrate() + " гр");
        simple_carbohydrate.setText(mGroup.get(groupPosition).getSimple_carbohydrates() + " гр");
        complex_carbohydrate.setText(mGroup.get(groupPosition).getComplex_carbohydrate() + " гр");
        fat.setText(mGroup.get(groupPosition).getFat() + " гр");
        saturated_fat.setText(mGroup.get(groupPosition).getSaturated_fat() + " гр");
        trans_fat.setText(mGroup.get(groupPosition).getTrans_fat() + " гр");
        omega9.setText(mGroup.get(groupPosition).getOmega_9() + " мг");
        omega6.setText(mGroup.get(groupPosition).getOmega_6() + " мг");
        omega3.setText(mGroup.get(groupPosition).getOmega_3() + " мг");
        ala.setText(mGroup.get(groupPosition).getAla() + " мг");
        dha.setText(mGroup.get(groupPosition).getDha() + " мг");
        epa.setText(mGroup.get(groupPosition).getEpa() + " мг");
        watter.setText(mGroup.get(groupPosition).getWatter() + " мл");
        cellulose.setText(mGroup.get(groupPosition).getCellulose() + " гр");
        salt.setText(mGroup.get(groupPosition).getSalt() + " гр");
        calcium.setText(mGroup.get(groupPosition).getCalcium() + " мг");
        potassium.setText(mGroup.get(groupPosition).getPotassium() + " мг");

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void FindViewParent(View root){
        up_linear = root.findViewById(R.id.up_linear);
        name = root.findViewById(R.id.name_product);
        weight = root.findViewById(R.id.weight_food);
        all_fat = root.findViewById(R.id.fat);
        all_protein = root.findViewById(R.id.protein);
        all_calorie = root.findViewById(R.id.calorie);
        all_carbohydrate = root.findViewById(R.id.carbohydrate);
    }
    public void FindViewChild(View root){


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
        calorie = root.findViewById(R.id.calorie);

        potassium_text = root.findViewById(R.id.potassium_text);
        salt_text = root.findViewById(R.id.salt_text);
        calcium_text = root.findViewById(R.id.calcium_text);
        cellulose_text = root.findViewById(R.id.cellulose_text);
        watter_text = root.findViewById(R.id.watter_text);
        casein_protein_text = root.findViewById(R.id.casein_protein_text);
        agg_protein_text = root.findViewById(R.id.agg_protein_text);
        soy_protein_text = root.findViewById(R.id.soy_protein_text);
        whey_protein_text = root.findViewById(R.id.whey_protein_text);
        protein_text = root.findViewById(R.id.protein_text);
        complex_carbohydrate_text = root.findViewById(R.id.complex_carbohydrate_text);
        simple_carbohydrate_text = root.findViewById(R.id.simple_carbohydrates_text);
        carbohydrate_text = root.findViewById(R.id.carbohydrate_text);
        epa_text = root.findViewById(R.id.epa_text);
        dha_text = root.findViewById(R.id.dha_text);
        ala_text = root.findViewById(R.id.ala_text);
        omega3_text = root.findViewById(R.id.omega3_text);
        omega6_text = root.findViewById(R.id.omega6_text);
        omega9_text = root.findViewById(R.id.omega9_text);
        trans_fat_text = root.findViewById(R.id.trans_fat_text);
        saturated_fat_text = root.findViewById(R.id.saturated_fat_text);
        fat_text = root.findViewById(R.id.fat_text);

        calorie_text = root.findViewById(R.id.calorie_text);
        fat = root.findViewById(R.id.fat);
        calorie = root.findViewById(R.id.calorie);
        carbohydrate = root.findViewById(R.id.carbohydrate);
        protein = root.findViewById(R.id.protein);

    }
    private void ShowView() {

        if (TextUtils.isEmpty(watter.getText()) || watter.getText().equals(String.valueOf(0) + " мл")){
            watter.setVisibility(View.GONE);
            watter_text.setVisibility(View.GONE); }
        if (TextUtils.isEmpty(cellulose.getText()) || cellulose.getText().equals(String.valueOf(0) + " гр") ){
            cellulose.setVisibility(View.GONE);
            cellulose_text.setVisibility(View.GONE); }
        if (TextUtils.isEmpty(potassium.getText()) || potassium.getText().equals(String.valueOf(0) + " мг") ){
            potassium.setVisibility(View.GONE);
            potassium_text.setVisibility(View.GONE); }
        if (TextUtils.isEmpty(salt.getText()) || salt.getText().equals(String.valueOf(0) + " гр") ){
            salt.setVisibility(View.GONE);
            salt_text.setVisibility(View.GONE);  }
        if (TextUtils.isEmpty(calcium.getText()) || calcium.getText().equals(String.valueOf(0) + " мг") ){
            calcium.setVisibility(View.GONE);
            calcium_text.setVisibility(View.GONE);    }
        if (TextUtils.isEmpty(casein_protein.getText()) || casein_protein.getText().equals(String.valueOf(0) + " гр") ){
            casein_protein.setVisibility(View.GONE);
            casein_protein_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(agg_protein.getText()) || agg_protein.getText().equals(String.valueOf(0) + " гр") ){
            agg_protein.setVisibility(View.GONE);
            agg_protein_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(soy_protein.getText()) || soy_protein.getText().equals(String.valueOf(0) + " гр")){
            soy_protein.setVisibility(View.GONE);
            soy_protein_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(whey_protein.getText()) || whey_protein.getText().equals(String.valueOf(0) + " гр") ){
            whey_protein.setVisibility(View.GONE);
            whey_protein_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(protein.getText()) || protein.getText().equals(String.valueOf(0) + " гр") ){
            protein.setVisibility(View.GONE);
            protein_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(complex_carbohydrate.getText()) || complex_carbohydrate.getText().equals(String.valueOf(0) + " гр")){
            complex_carbohydrate.setVisibility(View.GONE);
            complex_carbohydrate_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(simple_carbohydrate.getText()) || simple_carbohydrate.getText().equals(String.valueOf(0) + " гр")){
            simple_carbohydrate.setVisibility(View.GONE);
            simple_carbohydrate_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(carbohydrate.getText()) || carbohydrate.getText().equals(String.valueOf(0) + " гр") ){
            carbohydrate.setVisibility(View.GONE);
            carbohydrate_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(epa.getText()) || epa.getText().equals(String.valueOf(0) + " мг") ){
            epa.setVisibility(View.GONE);
            epa_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(dha.getText()) || dha.getText().equals(String.valueOf(0) + " мг") ){
            dha_text.setVisibility(View.GONE);
            dha.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(ala.getText()) || ala.getText().equals(String.valueOf(0) + " мг") ){
            ala.setVisibility(View.GONE);
            ala_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(omega3.getText()) || omega3.getText().equals(String.valueOf(0) + " мг")){
            omega3.setVisibility(View.GONE);
            omega3_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(omega6.getText()) || omega6.getText().equals(String.valueOf(0) + " мг") ){
            omega6.setVisibility(View.GONE);
            omega6_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(omega9.getText()) || omega9.getText().equals(String.valueOf(0) + " мг") ){
            omega9.setVisibility(View.GONE);
            omega9_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(saturated_fat.getText()) || saturated_fat.getText().equals(String.valueOf(0) + " гр") ){
            saturated_fat.setVisibility(View.GONE);
            saturated_fat_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(trans_fat.getText()) || trans_fat.getText().equals(String.valueOf(0) + " гр") ){
            trans_fat.setVisibility(View.GONE);
            trans_fat_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(fat.getText()) || fat.getText().equals(String.valueOf(0) + " гр") ){
            fat.setVisibility(View.GONE);
            fat_text.setVisibility(View.GONE);        }
        if (TextUtils.isEmpty(calorie.getText()) || calorie.getText().equals(String.valueOf(0)) ||
                calorie.getText().equals(String.format( Locale.ROOT,"%.2f",0.00))){
            calorie.setVisibility(View.GONE);
            calorie_text.setVisibility(View.GONE);        }

    }

}
