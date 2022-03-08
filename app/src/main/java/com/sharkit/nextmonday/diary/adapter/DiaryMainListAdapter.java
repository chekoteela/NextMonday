package com.sharkit.nextmonday.diary.adapter;

import static com.sharkit.nextmonday.diary.constant.DiaryConstant.DATE_FOR_CREATE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DiaryMainListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<List<DiaryTask>> diaryTasks;

    public DiaryMainListAdapter(Context context, List<List<DiaryTask>> diaryTasks) {
        this.context = context;
        this.diaryTasks = diaryTasks;
    }

    @Override
    public int getGroupCount() {
        return diaryTasks.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.diary_main_parent_list, null);
        }
        Widget widget = Widget.findByView(convertView);

        Bundle bundle = new Bundle();
        bundle.putString(DATE_FOR_CREATE, new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTimeInMillis()));


        widget.getImageView().getPlus().setOnClickListener(v -> Navigation.findNavController((Activity) context, R.id.nav_host_fragment)
        .navigate(R.id.navigation_diary_create_task, bundle));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.diary_main_child_item, null);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
