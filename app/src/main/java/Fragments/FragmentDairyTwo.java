package Fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;
import com.sharkit.nextmonday.Users.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentDairyTwo extends Fragment  {
     static final String TAG = "qwerty";
     FirebaseAuth mAuth;
     FirebaseDatabase fdb;
     RadioButton everyday,selectDay;
     CheckBox pn,vt,sr,cht,pt,sb,nd;
     DatabaseHelper databaseHelper;
     SQLiteDatabase db;
    private AdView mAdView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_two, container, false);
        EditText write_target = root.findViewById(R.id.write_target);
        Switch take_time = root.findViewById(R.id.take_time);
        Switch repeat = root.findViewById(R.id.repeat);
        Button addTarget = root.findViewById(R.id.add);

        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        Target.setMinutes(-1);
        Target.setHours(-1);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();

        mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        addTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(write_target.getText().toString())){
                    Toast.makeText(getContext(),"Введите текст напоминания",Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!repeat.isChecked()) {
                    repeat.setChecked(false);
                    Target.setR(0);
                    Target.setR_monday(0);
                    Target.setR_tuesday(0);
                    Target.setR_wednesday(0);
                    Target.setR_thursday(0);
                    Target.setR_friday(0);
                    Target.setR_saturday(0);
                    Target.setR_sunday(0);
                }

                Calendar calendar = Calendar.getInstance();
                Target.setTextTarget(write_target.getText().toString());

                String time = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH)
                        + "-" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
                try {
                    db.execSQL("INSERT INTO " + databaseHelper.TABLE + " VALUES('" + mAuth.getCurrentUser().getUid() + "','" + Target.getTextTarget() + "','" +
                            +0 + "','" +
                            +Target.getR_sunday() + "','" +
                            +Target.getR_saturday() + "','" +
                            +Target.getR_friday() + "','" +
                            +Target.getR_thursday() + "','" +
                            +Target.getR_wednesday() + "','" +
                            +Target.getR_tuesday() + "','" +
                            +Target.getR_monday() + "','" +
                            +Target.getR() + "','" +
                            +Target.getMinutes() + "','" +
                            +Target.getHours() + "','" +
                            +Target.getMonth() + "','" +
                            +Target.getDay() + "','" +
                            +Target.getYear() + "','" + time + "');");

                    Toast.makeText(getContext(),"Задача успешно добавлена",Toast.LENGTH_SHORT).show();

                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_diary);
                }catch (SQLiteConstraintException e){
                    Toast.makeText(getContext(),"Запись на данное время уже существует",Toast.LENGTH_SHORT).show();

                }
                
            }
        });

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(repeat.isChecked()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View select = layoutInflater.inflate(R.layout.list_radio_button, null);
                    everyday = select.findViewById(R.id.everyday);
                    selectDay = select.findViewById(R.id.selectDay);
                    pn = select.findViewById(R.id.pn);
                    vt = select.findViewById(R.id.vt);
                    sr = select.findViewById(R.id.sr);
                    cht = select.findViewById(R.id.cht);
                    pt = select.findViewById(R.id.pt);
                    sb = select.findViewById(R.id.sb);
                    nd = select.findViewById(R.id.nd);
                    LinearLayout checkBoxList = select.findViewById(R.id.checkboxList);

                    Target.setR(1);

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
                            if (selectDay.isChecked() == true &&
                                    pn.isChecked() == false &&
                                    vt.isChecked() == false &&
                                    sr.isChecked() == false &&
                                    cht.isChecked() == false &&
                                    pt.isChecked() == false &&
                                    sb.isChecked() == false &&
                                    nd.isChecked() == false ){
                                Target.setR(0);
                                repeat.setChecked(false);
                                return;
                            }

                            if (selectDay.isChecked()){

                                Target.setR(0);

                                if (pn.isChecked()){
                                    Target.setR_monday(1);
                                }    else {
                                    Target.setR_monday(0);
                                }
                                if (vt.isChecked()){
                                    Target.setR_tuesday(1);
                                }         else {
                                    Target.setR_tuesday(0);
                                }
                                if (sr.isChecked()){
                                    Target.setR_wednesday(1);
                                }        else {
                                    Target.setR_wednesday(0);
                                }
                                if (cht.isChecked()){
                                    Target.setR_thursday(1);
                                }         else {
                                    Target.setR_thursday(0);
                                }
                                if (pt.isChecked()){
                                    Target.setR_friday(1);
                                }          else {
                                    Target.setR_friday(0);
                                }
                                if (sb.isChecked()){
                                    Target.setR_saturday(1);
                                }         else {
                                    Target.setR_saturday(0);
                                }
                                if (nd.isChecked()){
                                    Target.setR_sunday(1);
                                } else {
                                    Target.setR_sunday(0);
                                }

                                if (pn.isChecked() && vt.isChecked() && sr.isChecked() && cht.isChecked() && pt.isChecked() && sb.isChecked() && nd.isChecked()){
                                    Target.setR(1);
                                    pn.setChecked(false);
                                    Target.setR_monday(0);
                                    vt.setChecked(false);
                                    Target.setR_tuesday(0);
                                    sr.setChecked(false);
                                    Target.setR_wednesday(0);
                                    cht.setChecked(false);
                                    Target.setR_thursday(0);
                                    pt.setChecked(false);
                                    Target.setR_friday(0);
                                    sb.setChecked(false);
                                    Target.setR_saturday(0);
                                    nd.setChecked(false);
                                    Target.setR_sunday(0);
                                }
                            }else if (everyday.isChecked()){
                                Target.setR(1);
                                pn.setChecked(false);
                                Target.setR_monday(0);
                                vt.setChecked(false);
                                Target.setR_tuesday(0);
                                sr.setChecked(false);
                                Target.setR_wednesday(0);
                                cht.setChecked(false);
                                Target.setR_thursday(0);
                                pt.setChecked(false);
                                Target.setR_friday(0);
                                sb.setChecked(false);
                                Target.setR_saturday(0);
                                nd.setChecked(false);
                                Target.setR_sunday(0);
                            }
                        }
                    });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        repeat.setChecked(false);
                    }
                });
                    dialog.setView(select);
                    dialog.show();

                }else if (!repeat.isChecked()) {
                    repeat.setChecked(false);
                    Target.setR(0);
                    Target.setR_monday(0);
                    Target.setR_tuesday(0);
                    Target.setR_wednesday(0);
                    Target.setR_thursday(0);
                    Target.setR_friday(0);
                    Target.setR_saturday(0);
                    Target.setR_sunday(0);
                }
            }
        });

        take_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (take_time.isChecked()){
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (getContext(), R.style.TimePickerTheme , new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Target.setHours(hourOfDay);
                            Target.setMinutes(minute);
                        }
                    },hour, minute, true);

            timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    take_time.setChecked(false);
                }
            });

            timePickerDialog.show();

        }else{
            take_time.setChecked(false);
            Target.setMinutes(-1);
            Target.setHours(-1);
            }}
        });

        return root;
    }



    @Override
    public void onStop() {
        db.close();
        super.onStop();
    }
}