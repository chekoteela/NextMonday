package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
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

import com.sharkit.nextmonday.Configuration.Configuration;
import com.sharkit.nextmonday.Configuration.Validation;
import com.sharkit.nextmonday.Exception.CustomToastComplete;
import com.sharkit.nextmonday.Exception.CustomToastException;
import com.sharkit.nextmonday.Exception.InternetException;
import com.sharkit.nextmonday.FirebaseEntity.TargetEntity;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.Days;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Week;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaryList extends BaseExpandableListAdapter {

    private LinearLayout linearLayout, lin;
    private TextView day, number, month, before, after, text_target, time_target;
    private CheckBox status;
    private ImageView plus;
    private RelativeLayout item;
    private ProgressBar progressBar;
    private final Context mContext;
    private final ArrayList<ArrayList<MyTarget>> mGroup;
    private final ArrayList<Week> mData;
    private final ArrayList<Days> mDay;
    private Validation validation;

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

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_diary, null);
        }
        findView(convertView);

        if (SelectToday(mData.get(groupPosition).getNumber()) == groupPosition) {
            lin.setBackgroundResource(R.drawable.dairy_exp_list_task_color);
        }

        validation = new Validation(mGroup, mDay, mContext);
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        progressBar.setProgress(setProgress(mGroup.get(groupPosition).size(), mData.get(groupPosition).getBefore()));
        number.setText(String.valueOf(mData.get(groupPosition).getNumber()));
        month.setText(mData.get(groupPosition).getMonth());
        day.setText(mData.get(groupPosition).getDay());
        after.setText(String.valueOf(mGroup.get(groupPosition).size()));
        before.setText(String.valueOf(mData.get(groupPosition).getBefore()));

        plus.setOnClickListener(v -> {
            if (validation.isValidYesterday(groupPosition)) {
                Target.setDay(mDay.get(groupPosition).getDay());
                Target.setMonth(mDay.get(groupPosition).getMonth());
                Target.setYear(mDay.get(groupPosition).getYear());
                navController.navigate(R.id.nav_plus_target);
            } else {
                try {
                    throw new CustomToastException(mContext, "Нельзя задать задачу задним числом");
                } catch (CustomToastException e) {
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }

    private int setProgress(int after, int before) {
        int a = 0;
        if (before != 0) {
            a = ((100 * before) / after);
        }
        return a;
    }

    @SuppressLint({"SimpleDateFormat", "InflateParams"})
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lst_view_castom, null);

        findView(convertView);
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        validation = new Validation(mGroup, mDay, mContext);

        text_target.setText(mGroup.get(groupPosition).get(childPosition).getName());

        time_target.setText(dateFormat.format(mGroup.get(groupPosition).get(childPosition).getTime_alarm()));
        if (validation.isNullTime(groupPosition, childPosition)) {
            time_target.setText("--:--");
        }
        status.setChecked(mGroup.get(groupPosition).get(childPosition).isStatus());

        item.setOnCreateContextMenuListener((menu, v, menuInfo) -> createContextMenu(menu, groupPosition, childPosition, navController));

        status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!Configuration.hasConnection(mContext)) {
                try {
                    throw new InternetException(mContext);
                } catch (InternetException e) {
                    e.printStackTrace();
                }
            } else {
                TargetData data = new TargetData(mContext);
                TargetEntity entity = new TargetEntity();
                entity.completeTarget(mGroup.get(groupPosition).get(childPosition).getTime_alarm(), isChecked);
                data.completeTarget(mGroup.get(groupPosition).get(childPosition).getTime_alarm(), isChecked, mGroup.get(groupPosition).get(childPosition).getDate());
                navController.navigate(R.id.nav_diary);
            }
        });
        return convertView;
    }

    private void createContextMenu(ContextMenu menu, int groupPosition, int childPosition, NavController navController) {
        menu.add("Изменить").setOnMenuItemClickListener(item -> {

            if (validation.isValidateCreateTarget(groupPosition, childPosition)) {
                Target.setDay(mDay.get(groupPosition).getDay());
                Target.setMonth(mDay.get(groupPosition).getMonth());
                Target.setYear(mDay.get(groupPosition).getYear());
                Target.setTimeForChange(mGroup.get(groupPosition).get(childPosition).getTime_alarm());
                navController.navigate(R.id.nav_change_target);
            }
            return true;
        });
        menu.add("Удалить").setOnMenuItemClickListener(item1 -> {
            if (!Configuration.hasConnection(mContext)) {
                try {
                    throw new InternetException(mContext);
                } catch (InternetException e) {
                    e.printStackTrace();
                }
                return false;
            }
            if (!validation.isValidYesterday(groupPosition)) {
                try {
                    throw new CustomToastException(mContext, "Нельзя идалить задачу задним числом");
                } catch (CustomToastException e) {
                    e.printStackTrace();
                }
                return false;
            }
            if (mGroup.get(groupPosition).get(childPosition).getRepeat().equals("select day not time") ||
                    mGroup.get(groupPosition).get(childPosition).getRepeat().equals("select day with time") ||
                    mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day not time") ||
                    mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day with time")) {
                createAlertDelete(groupPosition, childPosition);
                return false;
            }

            if (!validation.isValidDeleteTarget(groupPosition, childPosition)) {
                return false;
            }

            TargetData data = new TargetData(mContext);
            data.deleteItemForDate(mGroup.get(groupPosition).get(childPosition).getTime_alarm());
            TargetEntity entity = new TargetEntity();
            entity.deleteTarget(mGroup.get(groupPosition).get(childPosition).getTime_alarm());

            navController.navigate(R.id.nav_diary);
            return true;
        });
    }

    @SuppressLint("InflateParams")
    private void createAlertDelete(int i, int j) {
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.calculator_alert_exist, null);
        TargetData data = new TargetData(mContext);
        TargetEntity entity = new TargetEntity();
        TextView textView = view.findViewById(R.id.text_xml);
        textView.setText("Как именно вы хотите удалить задачу");
        if (validation.isValidDeleteTodayTarget(i, j)) {
            dialog.setPositiveButton("Удалить только эту", (dialog1, which) -> {
                data.deleteItemForDate(mGroup.get(i).get(j).getTime_alarm());
                entity.deleteTarget(mGroup.get(i).get(j).getTime_alarm());
                try {
                    throw new CustomToastComplete(mContext, "Задача успешно удалена");
                } catch (CustomToastComplete customToastComplete) {
                    customToastComplete.printStackTrace();
                }
                navController.navigate(R.id.nav_diary);
            });
        }
        dialog.setNegativeButton("Удалить все подобные", (dialog1, which) -> {
            data.deleteAllSimilarTarget(mGroup.get(i).get(j).getName());
            try {
                throw new CustomToastComplete(mContext, "Все задачи успешно удалены");
            } catch (CustomToastComplete customToastComplete) {
                customToastComplete.printStackTrace();
            }
            navController.navigate(R.id.nav_diary);
        });
        dialog.setOnCancelListener(DialogInterface::dismiss);
        dialog.setView(view);
        dialog.show();
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int SelectToday(int day) {
        int position = -1;
        Calendar calendar = Calendar.getInstance();


        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            position = 0;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            position = 1;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            position = 2;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {

            position = 3;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            position = 4;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            position = 5;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)) {
            position = 6;
        }

        return position;

    }

    private void findView(View convertView) {
        lin = convertView.findViewById(R.id.linear);
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
