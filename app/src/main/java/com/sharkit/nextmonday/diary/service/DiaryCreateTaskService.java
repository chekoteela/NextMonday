package com.sharkit.nextmonday.diary.service;

import static com.sharkit.nextmonday.configuration.constant.ButtonText.ACCEPT;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.entity.DayOfAlarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Setter;

public class DiaryCreateTaskService {

    private final Context context;
    private final Widget widget;
    @Setter
    private List<DayOfAlarm> daysOfAlarm;

    public DiaryCreateTaskService(Context context, Widget widget) {
        this.widget = widget;
        this.context = context;
    }

    @SuppressLint("NonConstantResourceId")
    public void showDayForRepeat() {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_list_of_repeat, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setView(view);
        Widget dialogWidget = Widget.findByView(view);

        dialogWidget.getRadioGroup().getRepeatRadioGroup()
                .setOnCheckedChangeListener((group, checkedId) -> {
                    switch (checkedId) {
                        case R.id.repeat_everyday_xml:
                            dialogWidget.getLinearLayout().getCheckBoxList().setVisibility(View.GONE);
                            daysOfAlarm = Arrays.asList(DayOfAlarm.values());
                            break;
                        case R.id.select_day_xml:
                            dialogWidget.getLinearLayout().getCheckBoxList().setVisibility(View.VISIBLE);
                            daysOfAlarm = new ArrayList<>();
                            break;
                    }
                });

        this.setCheckBoxActivity(dialogWidget);
        dialog.setPositiveButton(ACCEPT(context), (dialog1, which) -> {
            if (dialogWidget.getRadioButton().getRepeatEveryDay().isChecked()){
                daysOfAlarm = Arrays.asList(DayOfAlarm.values());
            }
            dialog1.dismiss();
        }).setOnCancelListener(dialog1 -> this.widget.getSwitch().getRepeat().setChecked(false)).show();
    }

    private void setCheckBoxActivity(Widget dialogWidget){
        dialogWidget.getCheckBox().isMonday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.MONDAY));
        dialogWidget.getCheckBox().isTuesday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.TUESDAY));
        dialogWidget.getCheckBox().isWednesday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.WEDNESDAY));
        dialogWidget.getCheckBox().isThursday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.THURSDAY));
        dialogWidget.getCheckBox().isFriday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.FRIDAY));
        dialogWidget.getCheckBox().isSaturday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.SATURDAY));
        dialogWidget.getCheckBox().isSunday().setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, DayOfAlarm.SUNDAY));

    }

    private void setDayForRepeat(boolean isChecked, DayOfAlarm dayOfAlarm) {
        if (isChecked) {
            daysOfAlarm.add(dayOfAlarm);
        } else {
            daysOfAlarm.remove(dayOfAlarm);
        }
    }

    public List<DayOfAlarm> getDaysOfAlarm() {
        return Optional.ofNullable(daysOfAlarm).orElse(new ArrayList<>());
    }
}
