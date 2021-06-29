package com.sharkit.nextmonday.ui.Diary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.Exception.CustomToastException;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiaryChangeTarget extends Fragment {

    private EditText text_target;
    private TextView repeat_text, time;
    private Switch take_time, repeat;
    private Button save;
    private TargetData targetData;
    private MyTarget target, newTarget;
    private RadioButton select_day, every_day;
    private LinearLayout list;
    private CheckBox monday, tuesday, wednesday,
            thursday, friday, saturday, sunday;
    private int hour = -1, minute = -1;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_four, container, false);
        findView(root);
        writeToForm();
        onClickListener();
        return root;


    }

    private void onClickListener() {
        take_time.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createTimePicker();
            }else {
                hour = -1;
                minute = -1;
            }
        });
        repeat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                createAlertRepeat();
            }else {
                noRepeat();
                repeat_text.setText(textRepeat(newTarget));
            }
        });
        save.setOnClickListener(v -> {
            saveChange();
        });
    }

    private void saveChange() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();
        Calendar instance = Calendar.getInstance();

        if (!repeat.isChecked() && !take_time.isChecked()) {
            newTarget.setRepeat("one more time");
        }
        //            myTarget.setDescription();       add description field
        newTarget.setName(text_target.getText().toString());
        if (minute == -1 && hour == -1) {
            calendar.set(Target.getYear(),
                    Target.getMonth(),
                    Target.getDay());
            newTarget.setTime_alarm(String.valueOf(instance.getTimeInMillis()));
        } else {
            calendar.set(Target.getYear(),
                    Target.getMonth(),
                    Target.getDay(), hour, minute);
            if (instance.getTimeInMillis() > calendar.getTimeInMillis()) {
                try {
                    throw new CustomToastException(getContext(), "Нельзя задавать задачу прошедшим временем");
                } catch (CustomToastException e) {
                    e.printStackTrace();
                }
                return;
            }
            newTarget.setTime_alarm(String.valueOf(calendar.getTimeInMillis()));
        }
        newTarget.setDate(dateFormat.format(calendar.getTimeInMillis()));
        TargetData targetData = new TargetData(getContext());
        targetData.updateItemForDate(Target.getTimeForChange(),newTarget);

    }

    private void writeDefaultTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTimeInMillis() > Long.parseLong(target.getTime_alarm())){
            time.setText("--:--");
            newTarget.setTime_alarm(target.getTime_alarm());
            take_time.setChecked(false);
        }else {
            newTarget.setTime_alarm(target.getTime_alarm());
            time.setText(dateFormat.format(Long.parseLong(target.getTime_alarm())));
            take_time.setChecked(true);
        }
    }

    private void writeDefaultRepeat() {
        newTarget.setRepeat(target.getRepeat());
        newTarget.setRepeat_monday(target.isRepeat_monday());
        newTarget.setRepeat_tuesday(target.isRepeat_tuesday());
        newTarget.setRepeat_wednesday(target.isRepeat_wednesday());
        newTarget.setRepeat_thursday(target.isRepeat_thursday());
        newTarget.setRepeat_friday(target.isRepeat_friday());
        newTarget.setRepeat_saturday(target.isRepeat_saturday());
        newTarget.setRepeat_sunday(target.isRepeat_sunday());
        repeat_text.setText(textRepeat(target));
    }


    private void createAlertRepeat() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_radio_button, null);
        findViewDialog(view);
        select_day.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            list.setVisibility(View.VISIBLE);
        });
        every_day.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            list.setVisibility(View.GONE);
        });
        dialog.setPositiveButton("Принять", (dialog1, which) -> {
            if (every_day.isChecked()){
                everyDayRepeat();
            }else if(select_day.isChecked()){
                selectDayRepeat();
            }
            repeat_text.setText(textRepeat(newTarget));
        }).setOnCancelListener(dialog1 -> {
            dialog1.dismiss();
            writeDefaultRepeat();
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
            newTarget.setRepeat("select day");
            newTarget.setRepeat_sunday(sunday.isChecked());
            newTarget.setRepeat_saturday(saturday.isChecked());
            newTarget.setRepeat_friday(friday.isChecked());
            newTarget.setRepeat_thursday(thursday.isChecked());
            newTarget.setRepeat_wednesday(wednesday.isChecked());
            newTarget.setRepeat_tuesday(tuesday.isChecked());
            newTarget.setRepeat_monday(monday.isChecked());
        }
    }

    private void everyDayRepeat() {
        newTarget.setRepeat("every day");
        newTarget.setRepeat_monday(true);
        newTarget.setRepeat_tuesday(true);
        newTarget.setRepeat_wednesday(true);
        newTarget.setRepeat_thursday(true);
        newTarget.setRepeat_friday(true);
        newTarget.setRepeat_saturday(true);
        newTarget.setRepeat_sunday(true);
    }

    private void noRepeat(){
        newTarget.setRepeat("not repeat");
        newTarget.setRepeat_monday(false);
        newTarget.setRepeat_tuesday(false);
        newTarget.setRepeat_wednesday(false);
        newTarget.setRepeat_thursday(false);
        newTarget.setRepeat_friday(false);
        newTarget.setRepeat_saturday(false);
        newTarget.setRepeat_sunday(false);
    }

    private void findViewDialog(View root) {
        list = root.findViewById(R.id.checkboxList);
        select_day = root.findViewById(R.id.selectDay_xml);
        every_day = root.findViewById(R.id.everyday_xml);
        monday = root.findViewById(R.id.pn);
        tuesday = root.findViewById(R.id.vt);
        wednesday = root.findViewById(R.id.sr);
        thursday = root.findViewById(R.id.cht);
        friday = root.findViewById(R.id.pt);
        saturday = root.findViewById(R.id.sb);
        sunday = root.findViewById(R.id.nd);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void createTimePicker() {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        TimePickerDialog dialog = new TimePickerDialog(getContext(),R.style.TimePickerTheme,(view, hourOfDay, minute1) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Target.getYear(),
                    Target.getMonth(),
                    Target.getDay(), hourOfDay,minute1);
            time.setText(format.format(calendar.getTimeInMillis()));
            hour = hourOfDay;
            minute = minute1;
            newTarget.setTime_alarm(String.valueOf(calendar.getTimeInMillis()));
        } ,hour, minute,true);
        dialog.setOnCancelListener(dialog1 -> {
            dialog1.dismiss();
            writeDefaultTime();
        });
        dialog.show();
    }


    private void writeToForm() {
        newTarget = new MyTarget();
        targetData = new TargetData(getContext());
        target = targetData.findItemForDate(Target.getTimeForChange());
        writeDefaultTime();
        text_target.setText(target.getName());
        repeat_text.setText(textRepeat(target));

    }


    private String textRepeat(MyTarget target) {
        String s = "";
        if (target.isRepeat_monday())
            s += "Пн";
        if (target.isRepeat_tuesday())
            s+=", Вт ";
        if (target.isRepeat_wednesday())
            s +=", Ср";
        if (target.isRepeat_thursday())
            s +=", Чт";
        if (target.isRepeat_friday())
            s += ", Пт";
        if (target.isRepeat_saturday())
            s += ", Сб";
        if (target.isRepeat_sunday())
            s += ", Нд";
        if (target.isRepeat_monday() && target.isRepeat_tuesday() &&
        target.isRepeat_wednesday() && target.isRepeat_thursday() &&
        target.isRepeat_friday() && target.isRepeat_saturday() && target.isRepeat_sunday()) {
            s = "Каждый день";
            repeat.setChecked(true);
        }
        if (target.isRepeat_monday() || target.isRepeat_tuesday() ||
                target.isRepeat_wednesday() || target.isRepeat_thursday() ||
                target.isRepeat_friday() || target.isRepeat_saturday() || target.isRepeat_sunday()) {
            repeat.setChecked(true);
        }
        if (!target.isRepeat_monday() && !target.isRepeat_tuesday() &&
        !target.isRepeat_wednesday() && !target.isRepeat_thursday() &&
        !target.isRepeat_friday() && !target.isRepeat_saturday() && !target.isRepeat_sunday()) {
            s = "Не повторять";
            repeat.setChecked(false);
        }
        return s;
    }

    private void findView(View root) {
        text_target = root.findViewById(R.id.textTarget);
        repeat_text = root.findViewById(R.id.repeat_xml);
        time = root.findViewById(R.id.change_time);
        take_time = root.findViewById(R.id.selectTime);
        repeat = root.findViewById(R.id.selectRepeat);
        save = root.findViewById(R.id.saveChange);
    }
}
