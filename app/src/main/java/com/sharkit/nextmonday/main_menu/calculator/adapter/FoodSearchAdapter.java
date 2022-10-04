package com.sharkit.nextmonday.main_menu.calculator.adapter;

import static java.util.Objects.isNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorAdapterWidget;
import com.sharkit.nextmonday.main_menu.calculator.domain.FoodInfo;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressLint("InflateParams")
public class FoodSearchAdapter extends BaseExpandableListAdapter {

    private final List<FoodInfo> foodInfo;
    private final Context context;

    @Override
    public int getGroupCount() {
        return this.foodInfo.size();
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return this.foodInfo.size();
    }

    @Override
    public FoodInfo getGroup(final int groupPosition) {
        return this.foodInfo.get(groupPosition);
    }

    @Override
    public FoodInfo getChild(final int groupPosition, final int childPosition) {
        return this.foodInfo.get(groupPosition);
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(final int groupPosition, final int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        if (isNull(convertView))
            convertView = LayoutInflater.from(this.context).inflate(R.layout.calculator_food_parent_list, null);

        final CalculatorAdapterWidget.ParentFoodItemWidget widget = CalculatorAdapterWidget.getInstance(convertView).getParentFoodItemWidget();

        widget.getFoodName().setText(this.getGroup(groupPosition).getName());
        widget.getProtein().setText(String.valueOf(this.getGroup(groupPosition).getProtein().getGeneralProteinWeight()));
        widget.getFat().setText(String.valueOf(this.getGroup(groupPosition).getFat().getGeneralFatWeight()));
        widget.getCarbohydrate().setText(String.valueOf(this.getGroup(groupPosition).getCarbohydrate().getGeneralCarbohydrateWeight()));
        widget.getCalorie().setText(String.valueOf(this.getGroup(groupPosition).getCalorie()));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
        if (isNull(convertView))
            convertView = LayoutInflater.from(this.context).inflate(R.layout.calculator_child_info, null);

        final CalculatorAdapterWidget.FoodInfoItemWidget widget = CalculatorAdapterWidget.getInstance(convertView).getFoodInfoItemWidget();

        this.fillOutFoodInfo(widget.getCalorie(), widget.getCalorieLayout(), this.getGroup(childPosition).getCalorie());
        this.fillOutFoodInfo(widget.getProtein(), widget.getProteinLayout(), this.getGroup(childPosition).getProtein().getGeneralProteinWeight());
        this.fillOutFoodInfo(widget.getWheyProtein(), widget.getWheyProteinLayout(), this.getGroup(childPosition).getProtein().getWheyProtein());
        this.fillOutFoodInfo(widget.getSoyProtein(), widget.getSoyProteinLayout(), this.getGroup(childPosition).getProtein().getSoyProtein());
        this.fillOutFoodInfo(widget.getAggProtein(), widget.getAggProteinLayout(), this.getGroup(childPosition).getProtein().getAggProtein());
        this.fillOutFoodInfo(widget.getCaseinProtein(), widget.getCaseinProteinLayout(), this.getGroup(childPosition).getProtein().getCaseinProtein());
        this.fillOutFoodInfo(widget.getCarbohydrate(), widget.getCarbohydrateLayout(), this.getGroup(childPosition).getCarbohydrate().getGeneralCarbohydrateWeight());
        this.fillOutFoodInfo(widget.getSimpleCarbohydrate(), widget.getSimpleCarbohydrateLayout(), this.getGroup(childPosition).getCarbohydrate().getSimpleCarbohydrate());
        this.fillOutFoodInfo(widget.getComplexCarbohydrate(), widget.getComplexCarbohydrateLayout(), this.getGroup(childPosition).getCarbohydrate().getComplexCarbohydrate());
        this.fillOutFoodInfo(widget.getFat(), widget.getFatLayout(), this.getGroup(childPosition).getFat().getGeneralFatWeight());
        this.fillOutFoodInfo(widget.getSaturatedFat(), widget.getSaturatedFatLayout(), this.getGroup(childPosition).getFat().getSaturatedFat());
        this.fillOutFoodInfo(widget.getTransFat(), widget.getTransFatLayout(), this.getGroup(childPosition).getFat().getTransFat());
        this.fillOutFoodInfo(widget.getOmega9(), widget.getOmega9Layout(), this.getGroup(childPosition).getFat().getOmega9());
        this.fillOutFoodInfo(widget.getOmega6(), widget.getOmega6Layout(), this.getGroup(childPosition).getFat().getOmega6());
        this.fillOutFoodInfo(widget.getOmega3(), widget.getOmega3Layout(), this.getGroup(childPosition).getFat().getOmega3().getOmega3());
        this.fillOutFoodInfo(widget.getDha(), widget.getDhaLayout(), this.getGroup(childPosition).getFat().getOmega3().getDha());
        this.fillOutFoodInfo(widget.getEpa(), widget.getEpaLayout(), this.getGroup(childPosition).getFat().getOmega3().getEpa());
        this.fillOutFoodInfo(widget.getAla(), widget.getAlaLayout(), this.getGroup(childPosition).getFat().getOmega3().getAla());
        this.fillOutFoodInfo(widget.getWater(), widget.getWaterLayout(), this.getGroup(childPosition).getWater());
        this.fillOutFoodInfo(widget.getCellulose(), widget.getCelluloseLayout(), this.getGroup(childPosition).getCellulose());
        this.fillOutFoodInfo(widget.getSalt(), widget.getSaltLayout(), this.getGroup(childPosition).getSalt());
        this.fillOutFoodInfo(widget.getCalcium(), widget.getCalciumLayout(), this.getGroup(childPosition).getCalcium());
        this.fillOutFoodInfo(widget.getPotassium(), widget.getPotassiumLayout(), this.getGroup(childPosition).getPotassium());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return false;
    }

    private <T> void fillOutFoodInfo(final TextView textView, final LinearLayout layout, final T value) {
        if (value.equals(0f)) {
            layout.setVisibility(View.GONE);
        } else {
            textView.setText(String.valueOf(value));
        }
    }

}
