package com.sharkit.nextmonday.diary.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.entity.DayOfAlarm;
import com.sharkit.nextmonday.diary.service.DiaryCreateTaskService;

import java.util.ArrayList;

public class DiaryCreateTask extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_create_task, container, false);
        Widget widget = Widget.findByView(view);
        DiaryCreateTaskService service = new DiaryCreateTaskService(getContext(), widget);

        widget.getSwitch().getRepeat().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    service.showDayForRepeat();
                }
            }
        });

        widget.getButton().getAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (widget.getSwitch().getRepeat().isChecked()) {
                    Log.i("TAGA", service.getDaysOfAlarm() + "");
                }else {
                    Log.i("TAGA", new ArrayList<DayOfAlarm>() + "");
                }
            }
        });
        return view;
    }
}
