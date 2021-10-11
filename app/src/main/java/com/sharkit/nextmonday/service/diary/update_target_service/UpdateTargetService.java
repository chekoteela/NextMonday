package com.sharkit.nextmonday.service.diary.update_target_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.constant.DayAndMonth;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
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


    public UpdateTargetService(ChildItemTargetDTO childItemTargetDTO) {
        this.childItemTargetDTO = childItemTargetDTO;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService writeToField() {
        TargetDataService targetDataService = new TargetDataService(context);
        TargetDateForAlarmDTO alarmDTO = targetDataService.getRepeatForAlarmDTO(childItemTargetDTO.getDate());
        textTarget.setText(childItemTargetDTO.getText());
        description.setText(childItemTargetDTO.getDescription());
        repeatDay.setText(getRepeats(alarmDTO));
        if (childItemTargetDTO.isAlarm()) {
            time.setText(new SimpleDateFormat("HH:mm").format(childItemTargetDTO.getDate()));
        } else {
            time.setText("--:--");
        }
        return this;
    }

    private String getRepeats(TargetDateForAlarmDTO alarmDTO){
        String repeats = "";
        if (alarmDTO.isRepeatMonday()){
            repeats = repeats + MONDAY;
        }
        if (alarmDTO.isRepeatTuesday()){
            repeats = repeats + TUESDAY;
        }
        if (alarmDTO.isRepeatWednesday()){
            repeats = repeats + WEDNESDAY;
        }
        if (alarmDTO.isRepeatThursday()){
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
        return repeats;
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
        return this;
    }
}
