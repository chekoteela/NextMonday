package com.sharkit.nextmonday.ui.Diary;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.Exception.CustomToastException;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;
import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

public class DiaryCreateNewTarget extends Fragment implements View.OnClickListener{

    public EditText text_target;
    public Switch take_time, repeat;
    public Button add;
    public RadioButton selectDay, everyDay;
    public CheckBox monday, tuesday, wednesday,
            thursday, friday, saturday, sunday;
    public LinearLayout list;
    public MyTarget myTarget;
    public int hour = -1,
            minutes = -1;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_two, container, false);
        myTarget = new MyTarget();
        onClickListener(this);
        findView(root);
        return root;
    }

    private void onClickListener(DiaryCreateNewTarget i) {
        repeat.setOnClickListener(i);
    }

    private void findView(View root) {
        text_target = root.findViewById(R.id.write_target_xml);
        take_time = root.findViewById(R.id.take_time_xml);
        add = root.findViewById(R.id.add_xml);
        repeat = root.findViewById(R.id.repeat_xml);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.repeat_xml:
                if (!repeat.isChecked()) {
                    createAlertRepeat();
                }else {
                    myTarget.setRepeat("dont repeat");
                    myTarget.setRepeat_friday(false);
                    myTarget.setRepeat_saturday(false);
                    myTarget.setRepeat_sunday(false);
                    myTarget.setRepeat_thursday(false);
                    myTarget.setRepeat_wednesday(false);
                    myTarget.setRepeat_tuesday(false);
                    myTarget.setRepeat_monday(false);
                }
                break;
            case R.id.selectDay_xml:
                list.setVisibility(View.VISIBLE);
                break;
            case R.id.everyday_xml:
                list.setVisibility(View.GONE);
                break;
            case R.id.add_xml:
                try {
                    createNewTarget();
                } catch (CustomToastException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.take_time_xml:
                createTimePicker();
                break;
        }

    }

    private void createTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
            }
        }, hour, minutes, true);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                hour = -1;
                minutes = -1;
                take_time.setChecked(false);
                timePickerDialog.cancel();
            }
        });
        timePickerDialog.show();
    }


    private void createNewTarget() throws CustomToastException {
        FirebaseAuth mAth = FirebaseAuth.getInstance();
        Calendar calendar = Calendar.getInstance();
        Calendar instance = Calendar.getInstance();


        if (hour != -1 && minutes != -1) {
            calendar.set(Target.getYear(),
                    Target.getMonth(),
                    Target.getDay(),
                    hour, minutes);
            myTarget.setTime_alarm(String.valueOf(calendar.getTimeInMillis()));
        }else {
            myTarget.setTime_alarm(String.valueOf(calendar.getTimeInMillis()));
        }
        if (calendar.getTimeInMillis() < instance.getTimeInMillis()){
            throw new CustomToastException(getContext(), "Нельзя задать задачу задним временем");
        }
        calendar.set(Target.getYear(), Target.getMonth(), Target.getDay());
        myTarget.setDate(String.valueOf(calendar.getTimeInMillis()));
        TargetData targetData = new TargetData(getContext());
        SQLiteDatabase db = targetData.getReadableDatabase();
        myTarget.setName(text_target.getText().toString());
        try {

        }catch (NullPointerException e){

        }
        targetData.addNewTarget(myTarget,db,mAth.getCurrentUser().getUid());
    }

    private void createAlertRepeat() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View root = inflater.inflate(R.layout.list_radio_button, null);
        findView(root);

        dialog.setPositiveButton("Принять", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (everyDay.isChecked()){
                    myTarget.setRepeat("every day");
                    myTarget.setRepeat_friday(true);
                    myTarget.setRepeat_saturday(true);
                    myTarget.setRepeat_sunday(true);
                    myTarget.setRepeat_thursday(true);
                    myTarget.setRepeat_wednesday(true);
                    myTarget.setRepeat_tuesday(true);
                    myTarget.setRepeat_monday(true);
                }else if(selectDay.isChecked()){
                    myTarget.setRepeat("repeat");
                    myTarget.setRepeat_monday(monday.isChecked());
                    myTarget.setRepeat_tuesday(tuesday.isChecked());
                    myTarget.setRepeat_wednesday(wednesday.isChecked());
                    myTarget.setRepeat_thursday(thursday.isChecked());
                    myTarget.setRepeat_friday(friday.isChecked());
                    myTarget.setRepeat_saturday(saturday.isChecked());
                    myTarget.setRepeat_sunday(sunday.isChecked());
                }
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myTarget.setRepeat("dont repeat");
                myTarget.setRepeat_friday(false);
                myTarget.setRepeat_saturday(false);
                myTarget.setRepeat_sunday(false);
                myTarget.setRepeat_thursday(false);
                myTarget.setRepeat_wednesday(false);
                myTarget.setRepeat_tuesday(false);
                myTarget.setRepeat_monday(false);
                repeat.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.setView(root);
        dialog.show();
    }
}
