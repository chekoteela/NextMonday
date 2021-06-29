package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class DiaryListFind extends BaseAdapter {
    private Context mContext;
    private ArrayList<MyTarget> mGroup;
    private CheckBox status;
    private TextView text_target, time_text, date_text;
    private RelativeLayout press;
    public DiaryListFind(Context mContext, ArrayList<MyTarget> mGroup) {
        this.mGroup = mGroup;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_custom_find, null);
        }
        findView(convertView);
        TargetData data = new TargetData(mContext);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        status.setChecked(mGroup.get(position).isStatus());
        text_target.setText(mGroup.get(position).getName());
        date_text.setText(mGroup.get(position).getDate());
        if (calendar.getTimeInMillis() > Long.parseLong(mGroup.get(position).getTime_alarm())){
            time_text.setText("--:--");
        }else {
            time_text.setText(timeFormat.format(Long.parseLong(mGroup.get(position).getTime_alarm())));
        }
        press.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add("Удалить").setOnMenuItemClickListener(item -> {
                data.deleteItemForDate(mGroup.get(position).getTime_alarm());
               return true;
            });
        });
        status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            data.completeTarget(mGroup.get(position).getTime_alarm(), isChecked);
        });

        return convertView;
    }

    private void findView(View convertView) {
        press = convertView.findViewById(R.id.layout);
        status = convertView.findViewById(R.id.checkFind);
        text_target = convertView.findViewById(R.id.textT);
        time_text = convertView.findViewById(R.id.timeT);
        date_text = convertView.findViewById(R.id.dateT);
    }
}
