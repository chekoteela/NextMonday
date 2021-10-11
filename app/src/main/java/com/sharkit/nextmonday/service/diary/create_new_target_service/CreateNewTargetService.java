package com.sharkit.nextmonday.service.diary.create_new_target_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ACCEPT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("InflateParams")
public class CreateNewTargetService implements LayoutService {
    private Context context;
    private final Long dateForCreate;
    private EditText textTarget, description;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch takeTime, repeat;
    private Button create;
    private RadioButton everyDay, selectDay;
    private CheckBox mon, tus, wed, thd, fri, sat, sun;
    private LinearLayout checkBoxList;
    private final TargetDiary targetDiary = new TargetDiary();
    private int hour, minutes;


    public CreateNewTargetService(Long dateForCreate) {
        this.dateForCreate = dateForCreate;
    }

    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        textTarget = root.findViewById(R.id.write_target_xml);
        takeTime = root.findViewById(R.id.take_time_xml);
        repeat = root.findViewById(R.id.repeat_xml);
        description = root.findViewById(R.id.add_description_xml);
        create = root.findViewById(R.id.add_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
    return this;
    }

    @Override
    public LayoutService activity() {
        create.setOnClickListener(v -> createTarget());
        takeTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createAlertTimePicker();
            }else {
                targetDiary.setTimeForAlarm(Calendar.getInstance().getTimeInMillis());
            }
        });
        repeat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!ValidationField.isValidCreateNewTargetField(textTarget, context)) {
                    return;
                }
                createAlertDialog();
            } else {
                new TargetDateForAlarmDTO().transform(targetDiary);
            }
        });
        return this;
    }


    private void createAlertTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            hour = hourOfDay;
            minutes = minute;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateForCreate);
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            targetDiary.setTimeForAlarm(calendar.getTimeInMillis());
        },hour, minutes, true);
        dialog.setOnDismissListener(DialogInterface::dismiss);
        dialog.show();
    }

    private void createAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.list_radio_button, null);
        findViewAlertDialog(view);
        setOnClick(dialog);
        dialog.setView(view);
        dialog.show();
    }


    private TargetDateForAlarmDTO writeSelectedDay() {
        TargetDateForAlarmDTO date = new TargetDateForAlarmDTO();
        if (selectDay.isChecked()) {
            date.setRepeatMonday(mon.isChecked());
            date.setRepeatTuesday(tus.isChecked());
            date.setRepeatWednesday(wed.isChecked());
            date.setRepeatThursday(thd.isChecked());
            date.setRepeatFriday(fri.isChecked());
            date.setRepeatSaturday(sat.isChecked());
            date.setRepeatSunday(sun.isChecked());
        } else if (everyDay.isChecked()) {
            date.setRepeatMonday(true);
            date.setRepeatTuesday(true);
            date.setRepeatWednesday(true);
            date.setRepeatThursday(true);
            date.setRepeatFriday(true);
            date.setRepeatSaturday(true);
            date.setRepeatSunday(true);
        }
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    private void createTarget() {
        TargetDataService service = new TargetDataService(context);
        targetDiary.setAlarm(takeTime.isChecked());
        targetDiary.setText(textTarget.getText().toString());
        targetDiary.setDescription(description.getText().toString());
        targetDiary.setDate(new SimpleDateFormat("dd.MM.yyyy").format(dateForCreate));
        service.create(targetDiary);
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
    }

    private void findViewAlertDialog(View root) {
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

    private void setOnClick(AlertDialog.Builder dialog) {
        dialog.setOnDismissListener(DialogInterface::dismiss);
        dialog.setPositiveButton(ACCEPT, (dialog1, which) -> {
            writeSelectedDay().transform(targetDiary);
            dialog1.dismiss();
        });
        everyDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxList.setVisibility(View.VISIBLE));
        selectDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxList.setVisibility(View.GONE));
    }
}
