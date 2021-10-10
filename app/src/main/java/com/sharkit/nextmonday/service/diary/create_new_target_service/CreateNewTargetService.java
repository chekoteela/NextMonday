package com.sharkit.nextmonday.service.diary.create_new_target_service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;
import com.sharkit.nextmonday.service.pop_ups.AlertDialogChooseDateForAlarm;

@SuppressLint("InflateParams")
public class CreateNewTargetService implements LayoutService {
    private Context context;
    private Long dateForCreate;
    private EditText textTarget, description;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch takeTime, repeat;
    private Button create;

    public CreateNewTargetService(Long dateForCreate) {
        this.dateForCreate = dateForCreate;
    }

    @Override
    public void writeToField() {

    }

    @Override
    public void findById(View root) {
        context = root.getContext();
        textTarget = root.findViewById(R.id.write_target_xml);
        takeTime = root.findViewById(R.id.take_time_xml);
        repeat = root.findViewById(R.id.repeat_xml);
        description = root.findViewById(R.id.add_description_xml);
        create = root.findViewById(R.id.add_xml);
    }

    @Override
    public void setAdaptive() {

    }

    @Override
    public void activity() {
        create.setOnClickListener(v -> createTarget());
        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TargetDiary targetDiary = new TargetDiary().transform(new AlertDialogChooseDateForAlarm().createAlertDialog(context).getResult());
            }
        });
    }
    private void createTarget() {
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
    }

}
