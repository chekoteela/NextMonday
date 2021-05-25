package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.PFC_today;

import java.util.ArrayList;

public class ModerationAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<DataPFC> mGroup;
    LinearLayout layout;
    TextView name, protein, fat, carbohydrate, calorie;

    public ModerationAdapter(Context context, ArrayList<DataPFC> group) {
        mContext = context;
        mGroup = group;
    }

    @Override
    public int getCount() {
        return mGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calculator_food_item_list, null);
        }
        FindView(convertView);

        protein.setText("Белки: " + mGroup.get(position).getProtein());
        carbohydrate.setText("Углеводы: " + mGroup.get(position).getCarbohydrate());
        fat.setText("Жиры: " + mGroup.get(position).getFat());
        name.setText(mGroup.get(position).getName());
        calorie.setText("Ккал: " + mGroup.get(position).getCalorie());

        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteListNutrition(position);
                navController.navigate(R.id.nav_settings_moderator_change);
            }
        });

        return convertView;
    }

    private void FindView(View convertView) {
        layout = convertView.findViewById(R.id.liner_for_long_press);
        name = convertView.findViewById(R.id.name_product);
        protein = convertView.findViewById(R.id.protein);
        calorie = convertView.findViewById(R.id.calorie);
        carbohydrate = convertView.findViewById(R.id.carbohydrate);
        fat = convertView.findViewById(R.id.fat);

    }

    private void WriteListNutrition(int position) {
        LocalDataPFC.setBar_code(mGroup.get(position).getBar_code());
        LocalDataPFC.setName(mGroup.get(position).getName());
        LocalDataPFC.setID(mGroup.get(position).getID());
        LocalDataPFC.setPortion(mGroup.get(position).getPortion());
        LocalDataPFC.setCalorie(mGroup.get(position).getCalorie());
        LocalDataPFC.setProtein(mGroup.get(position).getProtein());
        LocalDataPFC.setWhey_protein(mGroup.get(position).getWhey_protein());
        LocalDataPFC.setSoy_protein(mGroup.get(position).getSoy_protein());
        LocalDataPFC.setAgg_protein(mGroup.get(position).getAgg_protein());
        LocalDataPFC.setCasein_protein(mGroup.get(position).getCasein_protein());
        LocalDataPFC.setCarbohydrate(mGroup.get(position).getCarbohydrate());
        LocalDataPFC.setSimple_carbohydrates(mGroup.get(position).getSimple_carbohydrates());
        LocalDataPFC.setComplex_carbohydrate(mGroup.get(position).getComplex_carbohydrate());
        LocalDataPFC.setFat(mGroup.get(position).getFat());
        LocalDataPFC.setSaturated_fat(mGroup.get(position).getSaturated_fat());
        LocalDataPFC.setTrans_fat(mGroup.get(position).getTrans_fat());
        LocalDataPFC.setOmega_9(mGroup.get(position).getOmega_9());
        LocalDataPFC.setOmega_6(mGroup.get(position).getOmega_6());
        LocalDataPFC.setOmega_3(mGroup.get(position).getOmega_3());
        LocalDataPFC.setAla(mGroup.get(position).getAla());
        LocalDataPFC.setDha(mGroup.get(position).getDha());
        LocalDataPFC.setEpa(mGroup.get(position).getEpa());
        LocalDataPFC.setWatter(mGroup.get(position).getWatter());
        LocalDataPFC.setCellulose(mGroup.get(position).getCellulose());
        LocalDataPFC.setSalt(mGroup.get(position).getSalt());
        LocalDataPFC.setCalcium(mGroup.get(position).getCalcium());
        LocalDataPFC.setPotassium(mGroup.get(position).getPotassium());
        PFC_today.setBar_code(mGroup.get(position).getBar_code());
    }
}
