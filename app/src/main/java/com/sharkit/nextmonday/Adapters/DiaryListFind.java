package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaryListFind extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<MyTarget> mGroup;
    private CheckBox status;
    private TextView text_target, time_text, date_text, description;
    private RelativeLayout press;
    public DiaryListFind(Context mContext, ArrayList<MyTarget> mGroup) {
        this.mGroup = mGroup;
        this.mContext = mContext;
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

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_custom_find, null);
        }
        findView(convertView);
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        TargetData data = new TargetData(mContext);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        status.setChecked(mGroup.get(groupPosition).isStatus());
        text_target.setText(mGroup.get(groupPosition).getName());
        date_text.setText(mGroup.get(groupPosition).getDate());
        if (calendar.getTimeInMillis() > mGroup.get(groupPosition).getTime_alarm()){
            time_text.setText("--:--");
        }else {
            time_text.setText(timeFormat.format(mGroup.get(groupPosition).getTime_alarm()));
        }
        status.setOnCheckedChangeListener((buttonView, isChecked) ->{
            data.completeTarget(mGroup.get(groupPosition).getTime_alarm(), isChecked);
            navController.navigate(R.id.nav_search);
        });

        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, null);
        }
        findView(convertView);

        if (mGroup.get(groupPosition).getDescription().equals("null")){
            description.setText("Описание отсутствует");
        }else
        description.setText(mGroup.get(groupPosition).getDescription());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private void findView(View convertView) {
        press = convertView.findViewById(R.id.layout);
        status = convertView.findViewById(R.id.checkFind);
        text_target = convertView.findViewById(R.id.textT);
        time_text = convertView.findViewById(R.id.timeT);
        date_text = convertView.findViewById(R.id.dateT);
        description = convertView.findViewById(R.id.text_xml);
    }
}
