package com.sharkit.nextmonday.diary.service;

import static com.sharkit.nextmonday.configuration.constant.ButtonText.ACCEPT;
import static com.sharkit.nextmonday.diary.constant.DiaryConstant.EMPTY_TIME_FORMAT;
import static com.sharkit.nextmonday.diary.transformer.DateTransformer.toDayName;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.enums.DayOfAlarm;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class DiaryUpdateTaskService {

    private final Widget widget;
    private final Context context;
    private final DiaryTask diaryTask;

    public DiaryUpdateTaskService(Widget widget, Context context, DiaryTask diaryTask) {
        this.widget = widget;
        this.context = context;
        this.diaryTask = diaryTask;
        writeFields();
    }

    @SuppressLint("NewApi")
    private void writeFields() {
        List<String> days = new ArrayList<>();
        diaryTask.getDaysOfAlarm()
                .forEach(dayOfAlarm -> days.add(toDayName(context, dayOfAlarm)));

        if (diaryTask.getHour() == -1 || diaryTask.getMinute() == -1) {
            widget.getTextView().getRangTime().setText(EMPTY_TIME_FORMAT);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, diaryTask.getHour());
            calendar.set(Calendar.MINUTE, diaryTask.getMinute());
            widget.getTextView().getRangTime().setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTimeInMillis()));
            widget.getSwitch().getTakeTime().setChecked(true);
        }
        widget.getSwitch().getRepeat().setChecked(!diaryTask.getDaysOfAlarm().isEmpty());
        widget.getTextField().getNameOfTask().setText(diaryTask.getNameOfTask());
        widget.getTextView().getRepeatDays().setText(String.join(", ", days));
        widget.getTextField().getDescription().setText(diaryTask.getDescription());
    }

    public void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.time_picker_style, (view, hourOfDay, minuteOfHour) -> {
            diaryTask.setHour(hourOfDay);
            diaryTask.setMinute(minuteOfHour);
            writeFields();
        }, diaryTask.getHour(), diaryTask.getMinute(), true);
        timePickerDialog.setOnCancelListener(dialog ->{
            widget.getSwitch().getTakeTime().setChecked(false);
            widget.getTextView().getRangTime().setText(EMPTY_TIME_FORMAT);
        });
        timePickerDialog.show();
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
                            diaryTask.setDaysOfAlarm(Arrays.asList(DayOfAlarm.values()));
                            break;
                        case R.id.select_day_xml:
                            dialogWidget.getLinearLayout().getCheckBoxList().setVisibility(View.VISIBLE);
                            diaryTask.setDaysOfAlarm(new ArrayList<>());
                            break;
                    }
                });

        this.setCheckBoxActivity(dialogWidget);
        dialog.setPositiveButton(ACCEPT(context), (dialog1, which) -> {
            if (dialogWidget.getRadioButton().getRepeatEveryDay().isChecked()) {
                diaryTask.setDaysOfAlarm(Arrays.asList(DayOfAlarm.values()));
            }
            writeFields();
            dialog1.dismiss();
        });
        dialog.show();
    }

    private void setCheckBoxActivity(Widget dialogWidget) {
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
            diaryTask.getDaysOfAlarm().add(dayOfAlarm);
        } else {
            diaryTask.getDaysOfAlarm().remove(dayOfAlarm);
        }
    }

    public int getHour() {
        if (widget.getSwitch().getTakeTime().isChecked()) {
            return diaryTask.getHour();
        } else {
            return -1;
        }
    }

    public int getMinutes() {
        if (widget.getSwitch().getTakeTime().isChecked()) {
            return diaryTask.getMinute();
        } else {
            return -1;
        }
    }

    public List<DayOfAlarm> getDaysOfAlarm() {
        if (widget.getSwitch().getRepeat().isChecked()) {
            return Optional.ofNullable(diaryTask.getDaysOfAlarm()).orElse(new ArrayList<>());
        } else {
            return new ArrayList<>();
        }
    }
}
