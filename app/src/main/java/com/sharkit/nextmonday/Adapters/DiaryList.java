package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.Days;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Week;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaryList extends BaseExpandableListAdapter {

    public LinearLayout linearLayout;
    public TextView day, number, month, before, after, text_target, time_target;
    public CheckBox status;
    public ImageView plus;
    public RelativeLayout item;
    public ProgressBar progressBar;
    public Context mContext;
    public ArrayList<ArrayList<MyTarget>> mGroup;
    public ArrayList<Week> mData;
    public ArrayList<Days> mDay;
    public final String TAG = "qwerty";

    public DiaryList(Context mContext, ArrayList<ArrayList<MyTarget>> mGroup, ArrayList<Week> mData, ArrayList<Days> mDay) {
        this.mContext = mContext;
        this.mGroup = mGroup;
        this.mData = mData;
        this.mDay = mDay;
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

        NavController navController = Navigation.findNavController((Activity)mContext, R.id.nav_host_fragment);

        number.setText(String.valueOf(mData.get(groupPosition).getNumber()));
        month.setText(mData.get(groupPosition).getMonth());
        day.setText(mData.get(groupPosition).getDay());
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Target.setDay(mDay.get(groupPosition).getDay());
                Target.setMonth(mDay.get(groupPosition).getMonth());
                Target.setYear(mDay.get(groupPosition).getYear());
                navController.navigate(R.id.nav_plus_target);
            }
        });


        return convertView;
    }

    private boolean isToday(int position) {
        Calendar calendar = Calendar.getInstance();

        return true;
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lst_view_castom, null);

        findView(convertView);
        NavController navController = Navigation.findNavController((Activity)mContext, R.id.nav_host_fragment);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        TargetData data = new TargetData(mContext);
        text_target.setText(mGroup.get(groupPosition).get(childPosition).getName());

        time_target.setText(dateFormat.format(Long.parseLong(mGroup.get(groupPosition).get(childPosition).getTime_alarm())));
        if (calendar.getTimeInMillis() > Long.parseLong(mGroup.get(groupPosition).get(childPosition).getTime_alarm())){
            time_target.setText("--:--");
        }
        status.setChecked(mGroup.get(groupPosition).get(childPosition).isStatus());

        item.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add("Изменить").setOnMenuItemClickListener(item -> {
                Target.setDay(mDay.get(groupPosition).getDay());
                Target.setMonth(mDay.get(groupPosition).getMonth());
                Target.setYear(mDay.get(groupPosition).getYear());
                Target.setTimeForChange(mGroup.get(groupPosition).get(childPosition).getTime_alarm());
                navController.navigate(R.id.nav_change_target);
                return true;
            });
            menu.add("Удалить").setOnMenuItemClickListener(item1 -> {
                data.deleteItemForDate(mGroup.get(groupPosition).get(childPosition).getTime_alarm());
                return true;
            });
        });

        status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            data.completeTarget(mGroup.get(groupPosition).get(childPosition).getTime_alarm(), isChecked);
        });
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
        plus = convertView.findViewById(R.id.plus_xml);

        status = convertView.findViewById(R.id.completeTarget);
        text_target = convertView.findViewById(R.id.textTarget);
        time_target = convertView.findViewById(R.id.timeTarget);
        item = convertView.findViewById(R.id.create_child_item);
    }

}
