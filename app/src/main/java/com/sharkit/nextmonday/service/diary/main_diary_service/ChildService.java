package com.sharkit.nextmonday.service.diary.main_diary_service;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChildService implements LayoutService {
    private CheckBox checkBox;
    private TextView text, time;
    private final ChildItemTargetDTO targetDiary;

    public ChildService(ChildItemTargetDTO targetDiary) {
        this.targetDiary = targetDiary;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void writeToField() {
        text.setText(targetDiary.getText());
        checkBox.setChecked(targetDiary.isStatus());
        if (Calendar.getInstance().getTimeInMillis() < targetDiary.getDate()) {
            time.setText(new SimpleDateFormat("HH:mm").format(targetDiary.getDate()));
        }else {
            time.setText("--:--");
        }
    }

    @Override
    public void findById(View root) {
        checkBox = root.findViewById(R.id.completeTarget);
        text = root.findViewById(R.id.textTarget);
        time = root.findViewById(R.id.timeTarget);
    }

    @Override
    public void setAdaptive() {

    }

    @Override
    public void activity() {

    }
}
