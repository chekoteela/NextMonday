package com.sharkit.nextmonday.adapter.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.service.diary.all_list_of_target.ChildItemService;
import com.sharkit.nextmonday.service.diary.all_list_of_target.ParentItemService;

import java.util.ArrayList;

@SuppressLint("InflateParams")

public class AllListOfItem extends BaseExpandableListAdapter {
    private final Context context;
    private final ArrayList<ChildItemTargetDTO> group;

    public AllListOfItem(Context context, ArrayList<ChildItemTargetDTO> group) {
        this.context = context;
        this.group = group;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(childPosition).getDescription();
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_custom_find, null);
        }
        new ParentItemService(group.get(groupPosition))
                .findById(convertView)
                .writeToField()
                .setAdaptive()
                .activity();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.alert_simple_text, null);
        }
        new ChildItemService(group.get(childPosition).getDescription())
                .findById(convertView)
                .writeToField()
                .setAdaptive()
                .activity();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
