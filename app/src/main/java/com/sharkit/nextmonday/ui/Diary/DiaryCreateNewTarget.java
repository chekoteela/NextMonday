package com.sharkit.nextmonday.ui.Diary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.Exception.CustomToastComplete;
import com.sharkit.nextmonday.Exception.CustomToastException;
import com.sharkit.nextmonday.FirebaseEntity.TargetEntity;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class DiaryCreateNewTarget extends Fragment{

    public EditText text_target;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch take_time, repeat;
    public Button add;
    public RadioButton selectDay, everyDay;
    public CheckBox monday, tuesday, wednesday,
            thursday, friday, saturday, sunday;
    public LinearLayout list;
    public MyTarget myTarget;
    public int hour = -1,
            minutes = -1;
    public final String TAG = "qwerty";
    private String repeatString = "one", timeString = " not time";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_two, container, false);
        findView(root);
        myTarget = new MyTarget();
        onClickListener();

        return root;
    }

    @SuppressLint("SimpleDateFormat")
    private void onClickListener() {
        take_time.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createTimePicker();
            }else {
                minutes = -1;
                hour = -1;
            }
        });
        repeat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createAlertRepeat();
            }else{
                notRepeat();
            }

        });
        add.setOnClickListener(v -> {
            Calendar instance = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            myTarget.setRepeat(repeatString + timeString);
            myTarget.setDescription("");
            myTarget.setName(text_target.getText().toString());
        if (minutes == -1 && hour == -1){
            calendar.set(Target.getYear(),
                    Target.getMonth(),
                    Target.getDay());
            myTarget.setTime_alarm(instance.getTimeInMillis());
        }else{
            calendar.set(Target.getYear(),
                    Target.getMonth(),
                    Target.getDay(), hour,minutes,0);
            if (instance.getTimeInMillis() > calendar.getTimeInMillis()){
                try {
                    throw new CustomToastException(getContext(), "Нельзя задавать задачу прошедшим временем");
                } catch (CustomToastException e) {
                    e.printStackTrace();
                }
                return;
            }
            myTarget.setTime_alarm(calendar.getTimeInMillis());
        }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            myTarget.setDate(dateFormat.format(calendar.getTimeInMillis()));
            Log.d("qwerty", myTarget.getTime_alarm()+"");
            TargetData targetData = new TargetData(getContext());
            TargetEntity targetEntity = new TargetEntity();
            targetEntity.createNewTarget(myTarget);
            targetData.addNewTarget(myTarget);
            @SuppressLint("UseRequireInsteadOfGet")
            NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_diary);
            try {
                throw new CustomToastComplete(getContext(), "Задача успешно обновлена");
            } catch (CustomToastComplete customToastComplete) {
                customToastComplete.printStackTrace();
            }
        });
    }

    private void notRepeat() {
        repeatString = "one";
        myTarget.setRepeat_monday(false);
        myTarget.setRepeat_tuesday(false);
        myTarget.setRepeat_wednesday(false);
        myTarget.setRepeat_thursday(false);
        myTarget.setRepeat_friday(false);
        myTarget.setRepeat_saturday(false);
        myTarget.setRepeat_sunday(false);
    }

    private void createAlertRepeat() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_radio_button,null);
        findAlertView(view);
        selectDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                list.setVisibility(View.VISIBLE);
        });
        everyDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                list.setVisibility(View.GONE);
        });
        dialog.setNegativeButton("Отмена", (dialog1, which) -> {
            repeat.setChecked(false);
            dialog1.dismiss();
        });
        dialog.setPositiveButton("Принять",(dialog1, which) -> {
            if (everyDay.isChecked()){
                everyDayRepeat();
            }else if(selectDay.isChecked()){
                selectDayRepeat();
            }
        });
        dialog.setOnCancelListener(dialog1 -> {
            repeat.setChecked(false);
            dialog1.dismiss();
        });
        dialog.setView(view);
        dialog.show();
    }

    private void selectDayRepeat() {
        if (monday.isChecked() &&
        tuesday.isChecked() &&
        wednesday.isChecked() &&
        thursday.isChecked() &&
        friday.isChecked() &&
        saturday.isChecked() &&
        sunday.isChecked()){
            everyDayRepeat();
        }else {
            repeatString = "select day";
            myTarget.setRepeat_sunday(sunday.isChecked());
            myTarget.setRepeat_saturday(saturday.isChecked());
            myTarget.setRepeat_friday(friday.isChecked());
            myTarget.setRepeat_thursday(thursday.isChecked());
            myTarget.setRepeat_wednesday(wednesday.isChecked());
            myTarget.setRepeat_tuesday(tuesday.isChecked());
            myTarget.setRepeat_monday(monday.isChecked());
        }
    }

    private void everyDayRepeat() {
        repeatString = "every day";
        myTarget.setRepeat_monday(true);
        myTarget.setRepeat_tuesday(true);
        myTarget.setRepeat_wednesday(true);
        myTarget.setRepeat_thursday(true);
        myTarget.setRepeat_friday(true);
        myTarget.setRepeat_saturday(true);
        myTarget.setRepeat_sunday(true);
    }

    private void createTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            hour = hourOfDay;
            minutes = minute;
            timeString = " with time";
        }, hour, minutes, true);
        timePickerDialog.setOnCancelListener(dialog -> {
            hour = -1;
            minutes = -1;
            timeString = " not time";
            take_time.setChecked(false);
        });
        timePickerDialog.show();
    }

    private void findView(View root) {
        text_target = root.findViewById(R.id.write_target_xml);
        take_time = root.findViewById(R.id.take_time_xml);
        add = root.findViewById(R.id.add_xml);
        repeat = root.findViewById(R.id.repeat_xml);


    }
    private void findAlertView(View root){
        selectDay = root.findViewById(R.id.selectDay_xml);
        everyDay = root.findViewById(R.id.everyday_xml);
        monday = root.findViewById(R.id.pn);
        tuesday = root.findViewById(R.id.vt);
        wednesday = root.findViewById(R.id.sr);
        thursday = root.findViewById(R.id.cht);
        friday = root.findViewById(R.id.pt);
        saturday = root.findViewById(R.id.sb);
        sunday = root.findViewById(R.id.nd);
        list = root.findViewById(R.id.checkboxList);
    }
}
