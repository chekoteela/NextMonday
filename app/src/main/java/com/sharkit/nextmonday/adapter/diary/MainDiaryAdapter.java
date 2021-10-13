package com.sharkit.nextmonday.adapter.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.DayTargets;
import com.sharkit.nextmonday.service.diary.main_diary_service.builder.MainDiaryChildChildBuilder;
import com.sharkit.nextmonday.service.diary.main_diary_service.builder.MainDiaryParentChildBuilder;

import java.util.ArrayList;

@SuppressLint("InflateParams")
public class MainDiaryAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final ArrayList<DayTargets> mGroup;

    public MainDiaryAdapter(Context mContext, ArrayList<DayTargets> mGroup) {
        this.mContext = mContext;
        this.mGroup = mGroup;
    }

    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroup.get(groupPosition).getTargetDTOs().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroup.get(groupPosition).getTargetDTOs().get(childPosition);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_diary, null);
        }
        new MainDiaryParentChildBuilder(mGroup.get(groupPosition).getParentItemData())
                .findById(convertView)
                .writeToField()
                .setAdaptive()
                .activity()
                .build();

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lst_view_castom, null);
        }
            new MainDiaryChildChildBuilder(mGroup.get(groupPosition).getTargetDTOs().get(childPosition))
                    .findById(convertView)
                    .writeToField()
                    .setAdaptive()
                    .activity()
                    .build();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
