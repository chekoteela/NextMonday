package com.sharkit.nextmonday.service.pop_ups;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ACCEPT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;

public class AlertDialogChooseDateForAlarm extends AlertWindowMethods {
    private RadioButton everyDay, selectDay;
    private CheckBox mon, tus, wed, thd, fri, sat, sun;
    private LinearLayout checkBoxList;
    private TargetDateForAlarmDTO date;

    @Override
    public AlertDialogChooseDateForAlarm createAlertDialog(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.list_radio_button, null);
        findViewAlertDialog(view);
        date = new TargetDateForAlarmDTO();
        activity(dialog);
        dialog.setView(view);
        dialog.show();
        return this;
    }

    @Override
    public AlertDialogChooseDateForAlarm createAlertDialog(Context context, int themeId) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, themeId);
        View view = LayoutInflater.from(context).inflate(R.layout.list_radio_button, null);
        findViewAlertDialog(view);
        date = new TargetDateForAlarmDTO();
        activity(dialog);
        dialog.setView(view);
        dialog.show();
        return this;
    }

    @Override
    protected void findViewAlertDialog(View root) {
        everyDay = root.findViewById(R.id.everyday_xml);
        selectDay = root.findViewById(R.id.selectDay_xml);
        checkBoxList = root.findViewById(R.id.checkboxList_xml);
        mon = root.findViewById(R.id.monday_xml);
        tus = root.findViewById(R.id.tuesday_xml);
        wed = root.findViewById(R.id.wednesday_xml);
        thd = root.findViewById(R.id.thursday_xml);
        fri = root.findViewById(R.id.friday_xml);
        sat = root.findViewById(R.id.saturday_xml);
        sun = root.findViewById(R.id.sunday_xml);
    }

    @Override
    protected void activity(AlertDialog.Builder dialog) {
        dialog.setOnDismissListener(DialogInterface::dismiss);
        dialog.setPositiveButton(ACCEPT, (dialog1, which) -> {
            writeSelectedDay();
            dialog1.dismiss();
        });
        everyDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxList.setVisibility(View.VISIBLE));
        selectDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxList.setVisibility(View.GONE));
    }

    private void writeSelectedDay() {
        if (selectDay.isSelected()){
            date.setRepeatMonday(mon.isSelected());
            date.setRepeatTuesday(tus.isSelected());
            date.setRepeatWednesday(wed.isSelected());
            date.setRepeatThursday(thd.isSelected());
            date.setRepeatFriday(fri.isSelected());
            date.setRepeatSaturday(sat.isSelected());
            date.setRepeatSunday(sun.isSelected());
        }else if (everyDay.isSelected()){
            date.setRepeatMonday(true);
            date.setRepeatTuesday(true);
            date.setRepeatWednesday(true);
            date.setRepeatThursday(true);
            date.setRepeatFriday(true);
            date.setRepeatSaturday(true);
            date.setRepeatSunday(true);
        }
    }

    public TargetDateForAlarmDTO getResult() {
        return date;
    }
}
