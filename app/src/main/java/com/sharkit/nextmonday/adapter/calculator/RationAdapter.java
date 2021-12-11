package com.sharkit.nextmonday.adapter.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.entity.calculator.RationNutrition;
import com.sharkit.nextmonday.service.calculator.ration_service.ChildItemService;
import com.sharkit.nextmonday.service.calculator.ration_service.ParentItemService;

import java.util.ArrayList;

@SuppressLint("InflateParams")
public class RationAdapter extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<GeneralDataPFCDTO>> dataDTOs;
    private final ArrayList<RationNutrition> rationNutrition;
    private final Context context;

    public RationAdapter(ArrayList<RationNutrition> rationNutrition, ArrayList<ArrayList<GeneralDataPFCDTO>> dataDTOs, Context context) {
        this.dataDTOs = dataDTOs;
        this.context = context;
        this.rationNutrition = rationNutrition;
    }

    @Override
    public int getGroupCount() {
        return dataDTOs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataDTOs.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataDTOs.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataDTOs.get(groupPosition).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calculator_food_parent_list, null);
        }

        new ParentItemService(rationNutrition.get(groupPosition))
                .findById(convertView)
                .writeToField()
                .setAdaptive()
                .activity();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calculator_food_item_list, null);
        }

        new ChildItemService(dataDTOs.get(groupPosition).get(childPosition))
                .findById(convertView)
                .writeToField()
                .activity()
                .setAdaptive();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
