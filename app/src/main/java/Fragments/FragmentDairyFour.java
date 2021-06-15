package Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Users;

public class FragmentDairyFour extends Fragment {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    String text;
    Boolean monday, tuesday, wednesday, thursday, friday, saturday,sunday, repeat;
    int h, m;
    String hour, minute;
    Cursor query;
    final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_four, container, false);
        Button saveChange = root.findViewById(R.id.saveChange);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;

        LinearLayout linear_time = root.findViewById(R.id.layout_time);
        LinearLayout linear_repeat = root.findViewById(R.id.layout_repeat);
        Button save = root.findViewById(R.id.saveChange);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/11);
        LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(-2,h/17);
        params_button.setMargins(h/28,h/85,h/28,h/85);

        linear_time.setLayoutParams(params);
        save.setLayoutParams(params_button);
        linear_repeat.setLayoutParams(params);

        FindTargetForChange(root);

        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveChangeTarget(root);
                Toast.makeText(getContext(),"Изменения внесены",Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.nav_diary);
            }
        });
        return root;
    }

    private void SaveChangeTarget(View root) {
            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getReadableDatabase();

            EditText editText = root.findViewById(R.id.textTarget);

            db.execSQL("UPDATE " + databaseHelper.TABLE + " SET " + databaseHelper.COLUMN_TEXT_TARGET + "= '" + editText.getText().toString() +"',"+
                    databaseHelper.COLUMN_REPEAT_MONDAY + " = '" + dayOfWeek(monday) + "'," +
                    databaseHelper.COLUMN_REPEAT_TUESDAY + " = '" + dayOfWeek(tuesday) + "'," +
                    databaseHelper.COLUMN_REPEAT_WEDNESDAY + " = '" + dayOfWeek(wednesday) + "'," +
                    databaseHelper.COLUMN_REPEAT_THURSDAY + " = '" + dayOfWeek(thursday) + "'," +
                    databaseHelper.COLUMN_REPEAT_FRIDAY + " = '" + dayOfWeek(friday) + "'," +
                    databaseHelper.COLUMN_REPEAT_SATURDAY + " = '" + dayOfWeek(saturday) + "'," +
                    databaseHelper.COLUMN_REPEAT_SUNDAY + " = '" + dayOfWeek(sunday) + "'," +
                    databaseHelper.COLUMN_REPEAT + " = '" + dayOfWeek(repeat) + "'," +
                    databaseHelper.COLUMN_HOUR + " = '" + h + "'," +
                    databaseHelper.COLUMN_MINUTE + " = '" + m + "'" +
                    "  WHERE " + databaseHelper.COLUMN_TIME + " = '" + Target.getAll_time() + "'");
    }

    @SuppressLint("SetTextI18n")
    private void FindTargetForChange(View root) {
        SQLiteTarget();
        TextView timeT = root.findViewById(R.id.change_time);
        TextView  repeatT = root.findViewById(R.id.repeat);
        Switch selectTime = root.findViewById(R.id.selectTime);
        Switch selectRepeat = root.findViewById(R.id.selectRepeat);
        EditText textTarget = root.findViewById(R.id.textTarget);
        timeT.setText(hour + ":" + minute);
        textTarget.setText(text);
        repeatT.setText(RepeatDay(monday, tuesday, wednesday, thursday, friday, saturday,sunday, repeat));

        selectTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(selectTime.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(getContext(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minuted) {
                            hour = String.valueOf(hourOfDay);
                            minute = String.valueOf(minuted);
                            m = minuted;
                            h = hourOfDay;

                            if (hourOfDay < 10){
                                hour = "0" + hourOfDay;
                            }
                            if (minuted < 10){
                                minute = "0" + minuted;
                            }

                            timeT.setText(hour + ":" + minute);
                        }
                    },Integer.parseInt(hour),Integer.parseInt(minute),true);
                    timePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            selectTime.setChecked(false);
                        }
                    });
                    timePicker.show();
                }
            }
        });

        selectRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(selectRepeat.isChecked()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View select = layoutInflater.inflate(R.layout.list_radio_button, null);
                    RadioButton everyday = select.findViewById(R.id.everyday);
                    RadioButton selectDay = select.findViewById(R.id.selectDay);
                    CheckBox pn = select.findViewById(R.id.pn);
                    CheckBox vt = select.findViewById(R.id.vt);
                    CheckBox sr = select.findViewById(R.id.sr);
                    CheckBox cht = select.findViewById(R.id.cht);
                    CheckBox pt = select.findViewById(R.id.pt);
                    CheckBox sb = select.findViewById(R.id.sb);
                    CheckBox nd = select.findViewById(R.id.nd);
                    LinearLayout checkBoxList = select.findViewById(R.id.checkboxList);

                    pn.setChecked(monday);
                    vt.setChecked(tuesday);
                    sr.setChecked(wednesday);
                    cht.setChecked(thursday);
                    pt.setChecked(friday);
                    sb.setChecked(saturday);
                    nd.setChecked(sunday);

                    selectDay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkBoxList.setVisibility(View.VISIBLE);
                        }
                    });
                    everyday.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkBoxList.setVisibility(View.GONE);
                        }
                    });

                    dialog.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(selectDay.isChecked()){
                                repeat = false;
                                if (pn.isChecked()){
                                    monday = true;
                                }    else {
                                    monday = false;
                                }
                                if (vt.isChecked()){
                                    tuesday = true;
                                }         else {
                                    tuesday = false;
                                }
                                if (sr.isChecked()){
                                    wednesday = true;
                                }        else {
                                    wednesday = false;
                                }
                                if (cht.isChecked()){
                                    thursday = true;
                                }         else {
                                    thursday = false;
                                }
                                if (pt.isChecked()){
                                    friday = true;
                                }          else {
                                    friday = false;
                                }
                                if (sb.isChecked()){
                                    saturday = true;
                                }         else {
                                    saturday = false;
                                }
                                if (nd.isChecked()){
                                    sunday = true;
                                } else {
                                    sunday = false;
                                }
                            }
                            if(monday && tuesday && wednesday && thursday && friday && saturday && sunday){
                                monday = false;
                                tuesday = false;
                                wednesday = false;
                                thursday = false;
                                friday = false;
                                saturday = false;
                                sunday = false;
                                repeat = true;
                            }
                            if (everyday.isChecked()){
                                monday = false;
                                tuesday = false;
                                wednesday = false;
                                thursday = false;
                                friday = false;
                                saturday = false;
                                sunday = false;
                                repeat = true;
                            }

                            repeatT.setText(RepeatDay(monday, tuesday, wednesday, thursday, friday, saturday,sunday, repeat));
                        }
                    });

                    dialog.setView(select);
                    dialog.show();
                }
                else if(!selectRepeat.isChecked())
                {
                    monday = false;
                    tuesday = false;
                    wednesday = false;
                    thursday = false;
                    friday = false;
                    saturday = false;
                    sunday = false;
                    repeat = false;

                    repeatT.setText(RepeatDay(false, false, false, false,
                            false,false,false, false));
                    selectRepeat.setChecked(false);
                }
            }
        });
    }

    private String RepeatDay(Boolean monday,Boolean tuesday,Boolean wednesday,
                             Boolean thursday,Boolean friday,Boolean saturday,
                             Boolean sunday,Boolean repeat) {
        String repeatText = "";

        if (!(monday)) {
            repeatText = repeatText + "";
        } else if (monday) {
            repeatText = repeatText + "Пн ";
        }
        if (!(tuesday)) {
            repeatText = repeatText + "";
        } else if (tuesday) {
            repeatText = repeatText + "Вт ";
        }
        if (!(wednesday)) {
            repeatText = repeatText + "";
        } else if (wednesday) {
            repeatText = repeatText + "Ср ";
        }
        if (!(thursday)) {
            repeatText = repeatText + "";
        } else if (thursday) {
            repeatText = repeatText + "Чт ";
        }
        if (!(friday)) {
            repeatText = repeatText + "";
        } else if (friday) {
            repeatText = repeatText + "Пт ";
        }
        if (!(saturday)) {
            repeatText = repeatText + "";
        } else if (saturday) {
            repeatText = repeatText + "Сб ";
        }
        if (!(sunday)) {
            repeatText = repeatText + "";
        } else if (sunday) {
            repeatText = repeatText + "Нд ";
        }
        if (!(repeat)) {
            repeatText = repeatText + "";
        } else if (repeat) {
            repeatText =  " Каждий день ";
        }
        if(repeatText == ""){
            repeatText = "Не повторять";
        }
        return repeatText;
    }

    public void  SQLiteTarget(){
        databaseHelper = new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        databaseHelper.onCreate(db);
        query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " + databaseHelper.COLUMN_TIME +
                " = '" + Target.getAll_time() + "' AND " + databaseHelper.COLUMN_ID + " = '" + Users.getUID() + "'",null);

        while (query.moveToNext()) {
            sunday = dayOfWeek(query.getInt(3));
            saturday = dayOfWeek(query.getInt(4));
            friday = dayOfWeek(query.getInt(5));
            thursday = dayOfWeek(query.getInt(6));
            wednesday = dayOfWeek(query.getInt(7));
            tuesday = dayOfWeek(query.getInt(8));
            monday = dayOfWeek(query.getInt(9));
            repeat = dayOfWeek(query.getInt(10));
            text = query.getString(1);
            hour = String.valueOf(query.getInt(12));
            minute = String.valueOf(query.getInt(11));

            h = query.getInt(12);
            m = query.getInt(11);
            if (query.getInt(12) < 10){
                hour = "0" + hour;
            }
            if (query.getInt(11) < 10){
                minute = "0" + minute;
            }

            }
        Log.d(TAG, h +":" + m);
    }
    public int dayOfWeek(boolean num){
        if (!(num)) {
            return 0;
        } else if (num) {
            return 1;
        }
        return -1;
    }

    public boolean dayOfWeek (int num){
        switch (num){
            case 0:
                return false;
            case 1:
                return true;
        }
        return false;
    }

    @Override
    public void onStop() {
        db.close();
        query.close();
        super.onStop();
    }
}