package com.sharkit.nextmonday.service.diary.update_target_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ACCEPT;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.NOT_REPEAT;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.WITHOUT_TIME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.constant.DayAndMonth;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateTargetService extends DayAndMonth implements LayoutService {
    private final ChildItemTargetDTO childItemTargetDTO;
    private Context context;
    private EditText textTarget, description;
    private TextView repeatDay, time;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch repeat, takeTime;
    private Button save;
    private TargetDiary targetDiary;
    private RadioButton everyDay, selectDay;
    private CheckBox mon, tus, wed, thd, fri, sat, sun;
    private LinearLayout checkBoxList;
    private int hour, minutes;


    public UpdateTargetService(ChildItemTargetDTO childItemTargetDTO) {
        this.childItemTargetDTO = childItemTargetDTO;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService writeToField() {
        TargetDataService targetDataService = new TargetDataService(context);
        TargetDateForAlarmDTO alarmDTO = targetDataService.getRepeatForAlarmDTO(childItemTargetDTO.getDate());
        targetDiary = targetDataService.findByDate(childItemTargetDTO.getDate());
        textTarget.setText(childItemTargetDTO.getText());
        description.setText(childItemTargetDTO.getDescription());
        setRepeats(alarmDTO);
        if (alarmDTO.toArray().contains(true)) {
            repeat.setChecked(true);
            repeatDay.setText(NOT_REPEAT);
        }
        if (childItemTargetDTO.isAlarm()) {
            takeTime.setChecked(true);
            time.setText(new SimpleDateFormat("HH:mm").format(childItemTargetDTO.getDate()));
        } else {
            time.setText(WITHOUT_TIME);
        }
        return this;
    }

    private void setRepeats(TargetDateForAlarmDTO alarmDTO) {
        String repeats = "";
        if (alarmDTO.isRepeatMonday()) {
            repeats = repeats + MONDAY;
        }
        if (alarmDTO.isRepeatTuesday()) {
            repeats = repeats + TUESDAY;
        }
        if (alarmDTO.isRepeatWednesday()) {
            repeats = repeats + WEDNESDAY;
        }
        if (alarmDTO.isRepeatThursday()) {
            repeats = repeats + THURSDAY;
        }
        if (alarmDTO.isRepeatFriday()){
            repeats = repeats + FRIDAY;
        }
        if (alarmDTO.isRepeatSaturday()){
            repeats = repeats + SATURDAY;
        }
        if (alarmDTO.isRepeatSunday()){
            repeats = repeats + SUNDAY;
        }
        repeatDay.setText(repeats);
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        description = root.findViewById(R.id.description_xml);
        textTarget = root.findViewById(R.id.textTarget_xml);
        repeatDay = root.findViewById(R.id.repeat_day_xml);
        time = root.findViewById(R.id.time_xml);
        repeat = root.findViewById(R.id.repeat_xml);
        takeTime = root.findViewById(R.id.take_time_xml);
        save = root.findViewById(R.id.save_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        save.setOnClickListener(v -> {
            if (!ValidationField.isValidCreateNewTargetField(textTarget, context)) {
                return;
            }
            saveUpdates();
        });
        takeTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createAlertTimePicker();
            } else {
                time.setText(WITHOUT_TIME);
            }
        });
        repeat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createAlertDialog();
            } else {
                repeatDay.setText(NOT_REPEAT);
                new TargetDateForAlarmDTO().transform(targetDiary);
            }
        });
        return this;
    }

    @SuppressLint("SimpleDateFormat")
    private void saveUpdates() {
        TargetDataService service = new TargetDataService(context);
        targetDiary.setAlarm(takeTime.isChecked());
        targetDiary.setText(textTarget.getText().toString());
        targetDiary.setDescription(description.getText().toString());
        targetDiary.setDate(new SimpleDateFormat("dd.MM.yyyy").format(childItemTargetDTO.getDate()));
        if (targetDiary.getTimeForAlarm() == 0) {
            targetDiary.setTimeForAlarm(Calendar.getInstance().getTimeInMillis());
        }
        service.update(targetDiary, childItemTargetDTO.getDate());
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
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
        if (!date.toArray().contains(true)) {
            repeat.setChecked(false);
        }
        return date;
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void createAlertTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            hour = hourOfDay;
            minutes = minute;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(childItemTargetDTO.getDate());
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            time.setText(new SimpleDateFormat("HH:mm").format(calendar.getTimeInMillis()));
            targetDiary.setTimeForAlarm(calendar.getTimeInMillis());
        }, hour, minutes, true);
        dialog.setOnCancelListener(dialog12 -> {
            takeTime.setChecked(false);
            dialog12.dismiss();
        });
        dialog.show();
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
        dialog.setPositiveButton(ACCEPT, (dialog1, which) -> {
            setRepeats(writeSelectedDay());
            writeSelectedDay().transform(targetDiary);
            dialog1.dismiss();
        });
        dialog.setOnCancelListener(dialog12 -> {
            repeat.setChecked(false);
            dialog12.dismiss();
        });
        everyDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxList.setVisibility(View.VISIBLE));
        selectDay.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxList.setVisibility(View.GONE));
    }
}
