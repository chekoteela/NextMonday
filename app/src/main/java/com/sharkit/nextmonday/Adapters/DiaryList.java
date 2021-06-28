package com.sharkit.nextmonday.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Week;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class DiaryList extends BaseExpandableListAdapter {

     LinearLayout linearLayout;
     TextView day, number, month, before, after;
     ProgressBar progressBar;
     Context mContext;
    public ArrayList<ArrayList<MyTarget>> mGroup;
    public ArrayList<Week> mData;
    public final String TAG = "qwerty";

    public DiaryList(Context mContext, ArrayList<ArrayList<MyTarget>> mGroup, ArrayList<Week> mData) {
        this.mContext = mContext;
        this.mGroup = mGroup;
        this.mData = mData;
    }

    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroup.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroup.get(groupPosition).get(childPosition);
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_diary, null);
        }
        findView(convertView);

        number.setText(String.valueOf(mData.get(groupPosition).getNumber()));
        month.setText(mData.get(groupPosition).getMonth());
        day.setText(mData.get(groupPosition).getDay());


        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lst_view_castom, null);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private void findView(View convertView) {
        linearLayout = convertView.findViewById(R.id.lin_xml);
        day = convertView.findViewById(R.id.day_xml);
        number = convertView.findViewById(R.id.num_x);
        month = convertView.findViewById(R.id.month_xml);
        before = convertView.findViewById(R.id.before_xml);
        after = convertView.findViewById(R.id.after_xml);
        progressBar = convertView.findViewById(R.id.progress_bar_xml);
    }

}
