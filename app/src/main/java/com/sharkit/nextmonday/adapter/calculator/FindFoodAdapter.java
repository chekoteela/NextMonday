package com.sharkit.nextmonday.adapter.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.GeneralDataPFCDTO;
import com.sharkit.nextmonday.entity.calculator.PFC;
import com.sharkit.nextmonday.service.calculator.find_food.ChildItemService;
import com.sharkit.nextmonday.service.calculator.find_food.ParentItemService;

import java.util.ArrayList;

@SuppressLint("InflateParams")
public class FindFoodAdapter extends BaseExpandableListAdapter {
    private final ArrayList<PFC> pfcList;
    private final ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS;
    private final Context context;
    private String mealName;

    public FindFoodAdapter(ArrayList<PFC> pfcList, ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS, Context context) {
        this.pfcList = pfcList;
        this.generalDataPFCDTOS = generalDataPFCDTOS;
        this.context = context;
    }

    public FindFoodAdapter(ArrayList<PFC> pfcList, ArrayList<GeneralDataPFCDTO> generalDataPFCDTOS, Context context, String mealName) {
        this.pfcList = pfcList;
        this.generalDataPFCDTOS = generalDataPFCDTOS;
        this.context = context;
        this.mealName = mealName;
    }

    @Override
    public int getGroupCount() {
        return generalDataPFCDTOS.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return generalDataPFCDTOS.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return pfcList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.calculator_food_item_list, null);
        }
        new ParentItemService(generalDataPFCDTOS.get(groupPosition), mealName)
                .findById(convertView)
                .writeToField()
                .activity()
                .setAdaptive();

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.calculator_child_info, null);
        }
        new ChildItemService(pfcList.get(groupPosition))
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
