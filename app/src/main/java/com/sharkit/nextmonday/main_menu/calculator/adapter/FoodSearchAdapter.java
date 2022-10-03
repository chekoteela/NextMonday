package com.sharkit.nextmonday.main_menu.calculator.adapter;

import static java.util.Objects.isNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

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

        return convertView;
    }

    @Override
    public boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return false;
    }
}
