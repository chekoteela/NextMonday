package Fragments;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.BaseTarget;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.MyAlarm;
import com.sharkit.nextmonday.Adapters.MyExpandebleListAdapter;
import com.sharkit.nextmonday.Users.SizeTarget;
import com.sharkit.nextmonday.Users.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentDairy extends Fragment {
     ArrayList<String> arrayText, arrayCompleteT;
     ExpandableListAdapter adapter;
     FirebaseAuth mAuth;
     FirebaseDatabase fdb;
     DatabaseReference users;
     DatabaseHelper databaseHelper;
     SQLiteDatabase db;
     Cursor query;
     final String TAG = "qwerty";

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy, container, false);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Targets/MineTargets");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        databaseHelper.onCreate(db);
        SizeH(root);
        ReplayTarget();
        TimeForAlarm();
        SynchronizedDB();
        SynchronizedSQLiteDB();
        RefreshCompleteTarget();
        CustomExpandableListView(root);


        return root;
    }
    public void CustomExpandableListView(View root){

        ExpandableListView listView = root.findViewById(R.id.expListView);

        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> monday = new ArrayList<String>();
        ArrayList<String> tuesday = new ArrayList<String>();
        ArrayList<String> wednesday = new ArrayList<String>();
        ArrayList<String> thursday = new ArrayList<String>();
        ArrayList<String> friday = new ArrayList<String>();
        ArrayList<String> saturday = new ArrayList<String>();
        ArrayList<String> sunday = new ArrayList<String>();

        for(int i =0; i < SizeChildGroup(DayOfWeek.getMonday_numeric(),
                Target.getMonday_mouth(), DayOfWeek.getMonday_year()); i++){
            monday.add("item");
        }
        groups.add(monday);
        for(int i =0; i < SizeChildGroup(DayOfWeek.getTuesday_numeric(),
                Target.getTuesday_month(), DayOfWeek.getTuesday_year()); i++){
            tuesday.add("item");
        }
        groups.add(tuesday);
        for(int i =0; i < SizeChildGroup(DayOfWeek.getWednesday_numeric(),
                Target.getWednesday_month(), DayOfWeek.getWednesday_year()); i++){
            wednesday.add("item");
        }
        groups.add(wednesday);
        for(int i =0; i < SizeChildGroup(DayOfWeek.getThursday_numeric(),
                Target.getThursday_month(), DayOfWeek.getThursday_year()); i++){
            thursday.add("item");
        }
        groups.add(thursday);
        for(int i =0; i < SizeChildGroup(DayOfWeek.getFriday_numeric(),
                Target.getFriday_month(), DayOfWeek.getFriday_year()); i++){
            friday.add("item");
        }
        groups.add(friday);
        for(int i =0; i < SizeChildGroup(DayOfWeek.getSaturday_numeric(),
                Target.getSaturday_month(), DayOfWeek.getSaturday_year()); i++){
            saturday.add("item");
        }
        groups.add(saturday);
        for(int i =0; i < SizeChildGroup(DayOfWeek.getSunday_numeric(),
                Target.getSunday_month(), DayOfWeek.getSunday_year()); i++){
            sunday.add("item");
        }
        groups.add(sunday);
        SizeTarget.setMonAllTarget(monday.size());
        SizeTarget.setTusAllTarget(tuesday.size());
        SizeTarget.setWedAllTarget(wednesday.size());
        SizeTarget.setThdAllTarget(thursday.size());
        SizeTarget.setFriAllTarget(friday.size());
        SizeTarget.setSatAllTarget(saturday.size());
        SizeTarget.setSunAllTarget(sunday.size());



        adapter = new MyExpandebleListAdapter(getContext(), groups);
        listView.setAdapter(adapter);

    }

    private int SizeChildGroup(int day, int month,int year){
        db = databaseHelper.getReadableDatabase();
        arrayText = new ArrayList<>();
        query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " + databaseHelper.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid()+"'", null);

        while (query.moveToNext()) {
            if (query.getInt(14) == day &&
                    query.getInt(13) == month &&
                    query.getInt(15) == year) {
                arrayText.add(query.getString(1));
            }}
        return arrayText.size();
    }

    private static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void SynchronizedDB() {
        if (hasConnection(getContext()) == true) {
            Cursor query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " + databaseHelper.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid()+"'", null);
            while (query.moveToNext()) {

                users = fdb.getReference("Users" + "/" + mAuth.getCurrentUser().getUid() + "/Targets/MineTargets/" +
                        query.getString(16));
                BaseTarget target = new BaseTarget();

                target.setText_target(query.getString(1));
                target.setRepeat(query.getInt(10));
                target.setRepeat_monday(query.getInt(9));
                target.setRepeat_tuesday(query.getInt(8));
                target.setRepeat_wednesday(query.getInt(7));
                target.setRepeat_thursday(query.getInt(6));
                target.setRepeat_friday(query.getInt(5));
                target.setRepeat_saturday(query.getInt(4));
                target.setRepeat_sunday(query.getInt(3));
                target.setR_year(query.getInt(15));
                target.setR_month(query.getInt(13));
                target.setR_day(query.getInt(14));
                target.setR_hour(query.getInt(12));
                target.setR_minute(query.getInt(11));
                target.setComplete(query.getInt(2));
                target.setTime(query.getString(16));
                users.setValue(target);
            }
        }
    }

    private void SynchronizedSQLiteDB() {
        db = databaseHelper.getReadableDatabase();
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Targets/MineTargets");
        if(hasConnection(getContext())==true) {

            users.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    try {
                        BaseTarget target = snapshot.getValue(BaseTarget.class);
                        String time = target.getR_day() + "-" + target.getR_month() + "-" + target.getR_year() + " " + target.getR_hour() + ":" + target.getR_minute();
                        db.execSQL("INSERT INTO " + databaseHelper.TABLE + " VALUES('" + mAuth.getCurrentUser().getUid() + "','" + target.getText_target() + "','" +
                                +target.getComplete() + "','" +
                                +target.getRepeat_sunday() + "','" +
                                +target.getRepeat_saturday() + "','" +
                                +target.getRepeat_friday() + "','" +
                                +target.getRepeat_thursday() + "','" +
                                +target.getRepeat_wednesday() + "','" +
                                +target.getRepeat_tuesday() + "','" +
                                +target.getRepeat_monday() + "','" +
                                +target.getRepeat() + "','" +
                                +target.getR_minute() + "','" +
                                +target.getR_hour() + "','" +
                                +target.getR_month() + "','" +
                                +target.getR_day() + "','" +
                                +target.getR_year() + "','"
                                + target.getTime() + "');");

                    } catch (SQLiteConstraintException e) {
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void RefreshCompleteTarget(){

        SizeTarget.setMonCompleteTarget(SizeCompleteTarget(DayOfWeek.getMonday_numeric(),
                Target.getMonday_mouth(), DayOfWeek.getMonday_year()));
        SizeTarget.setTusCompleteTarget(SizeCompleteTarget(DayOfWeek.getTuesday_numeric(),
                Target.getTuesday_month(), DayOfWeek.getTuesday_year()));
        SizeTarget.setWedCompleteTarget(SizeCompleteTarget(DayOfWeek.getWednesday_numeric(),
                Target.getWednesday_month(), DayOfWeek.getWednesday_year()));
        SizeTarget.setThdCompleteTarget(SizeCompleteTarget(DayOfWeek.getThursday_numeric(),
                Target.getThursday_month(), DayOfWeek.getThursday_year()));
        SizeTarget.setFriCompleteTarget(SizeCompleteTarget(DayOfWeek.getFriday_numeric(),
                Target.getFriday_month(), DayOfWeek.getFriday_year()));
        SizeTarget.setSatCompleteTarget(SizeCompleteTarget(DayOfWeek.getSaturday_numeric(),
                Target.getSaturday_month(), DayOfWeek.getSaturday_year()));
        SizeTarget.setSunACompleteTarget(SizeCompleteTarget(DayOfWeek.getSunday_numeric(),
                Target.getSunday_month(), DayOfWeek.getSunday_year()));
    }
    private int SizeCompleteTarget(int day, int month, int year){
        db = databaseHelper.getReadableDatabase();
        arrayCompleteT = new ArrayList<>();
        query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " +
                databaseHelper.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid()+"'",null);
        while (query.moveToNext()){
            if ((query.getInt(14) == day &&
                    query.getInt(13) == month &&
                    query.getInt(15) == year &&
                    query.getInt(2) == 1)){
                arrayCompleteT.add(query.getInt(2)+"");
            }
        }
        return  arrayCompleteT.size();
    }

    private void TimeForAlarm(){
        db = databaseHelper.getReadableDatabase();
        int count = 0;

        query = db.rawQuery( "SELECT * FROM " + databaseHelper.TABLE + " WHERE " + databaseHelper.COLUMN_COMPLETE + " = 0 AND "
                + databaseHelper.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);
        while (query.moveToNext()){

                SetAlarm(query.getInt(14),
                        query.getInt(13),
                        query.getInt(15),
                        query.getInt(12),
                        query.getInt(11),
                        count);
                count++;
        }
    }

    private void SetAlarm(int day, int month, int year, int hour, int minute, int count) {

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        int nowYear = calendar1.get(Calendar.YEAR);
        int nowMoth = calendar1.get(Calendar.MONTH);
        int nowDay = calendar1.get(Calendar.DAY_OF_MONTH);
        int nowHour = calendar1.get(Calendar.HOUR);
        int nowMinute = calendar1.get(Calendar.MINUTE);

        if(query.getInt(15) < nowYear ||
                query.getInt(13) < nowMoth ||
                query.getInt(14) < nowDay ||
                query.getInt(12) < nowHour ||
                query.getInt(11) < nowMinute) {
            return;
        }
        try {
                calendar.set(
                        year, month, day, hour, minute, 0
                );
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), MyAlarm.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), count, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        }catch (IndexOutOfBoundsException e){}
    }

    private void ReplayTarget(){
        db = databaseHelper.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " +
                databaseHelper.COLUMN_YEAR + " = " + year + " AND " + databaseHelper.COLUMN_MONTH +" = " + month + " AND " +
                databaseHelper.COLUMN_DAY + " = " + day + " AND " + databaseHelper.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid()+"'",null);

        while (query.moveToNext()) {

            if (query.getInt(10) == 1) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time);
            }
            if(query.getInt(7) == 1 ){
                Calendar calendar1 = Calendar.getInstance();

                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
                }else {
                while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
                    calendar1.add(Calendar.DAY_OF_WEEK, 1);
                }
                String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
            }
            }
            if(query.getInt(9) == 1 ){
                Calendar calendar1 = Calendar.getInstance();

                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
                }else {
                while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    calendar1.add(Calendar.DAY_OF_WEEK, 1);
                }
                String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time);
            }
            }
            if(query.getInt(8) == 1 ){
                Calendar calendar1 = Calendar.getInstance();
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
                }else {
                while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                    calendar1.add(Calendar.DAY_OF_WEEK, 1);
                }
                String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time);
            }
            }
            if(query.getInt(6) == 1 ){
                Calendar calendar1 = Calendar.getInstance();
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
                }else {
                while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                    calendar1.add(Calendar.DAY_OF_WEEK, 1);
                }
                String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time);
            }
            }
            if(query.getInt(5) == 1 ){
                Calendar calendar1 = Calendar.getInstance();
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
                }else {
                while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                    calendar1.add(Calendar.DAY_OF_WEEK, 1);
                }
                String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time);
            }
            }
            if(query.getInt(4) == 1 ){
                Calendar calendar1 = Calendar.getInstance();
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time1);
                }else {
                while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                    calendar1.add(Calendar.DAY_OF_WEEK, 1);
                }
                String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                        + " " + query.getInt(12) + ":" + query.getInt(11);
                WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR),time);
            }
            }
            if(query.getInt(3) == 1 ) {
                Calendar calendar1 = Calendar.getInstance();
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calendar1.add(Calendar.DAY_OF_MONTH, 7);
                    String time1 = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.YEAR), time1);
                } else {
                    while (calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        calendar1.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    String time = calendar1.get(Calendar.DAY_OF_MONTH) + "-" + calendar1.get(Calendar.MONTH) + "-" + calendar1.get(Calendar.YEAR)
                            + " " + query.getInt(12) + ":" + query.getInt(11);
                    WriteReplayTarget(calendar1.get(Calendar.DAY_OF_MONTH), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.YEAR), time);
                }
            }
        }
    }

    private void WriteReplayTarget(int day, int month, int year, String time){
        try {
            db.execSQL("INSERT INTO " + databaseHelper.TABLE + " VALUES ('" +
                    mAuth.getCurrentUser().getUid() + "','" +
                    query.getString(1) + "','" +
                    0 + "','" +
                    query.getInt(3) + "','" +
                    query.getInt(4) + "','" +
                    query.getInt(5) + "','" +
                    query.getInt(6) + "','" +
                    query.getInt(7) + "','" +
                    query.getInt(8) + "','" +
                    query.getInt(9) + "','" +
                    query.getInt(10) + "','" +
                    query.getInt(11) + "','" +
                    query.getInt(12) + "','" +
                    month + "','" +
                    day + "','" +
                    year + "','" +
                    time + "');");
        }catch (SQLiteConstraintException e){}

    }
    public void SizeH(View root){

        LinearLayout lin  = root.findViewById(R.id.lin);

        lin.post(new Runnable() {
            @Override
            public void run() {
                int h = lin.getHeight();
                SizeTarget.setHeight(h);
            }
        });

    }

}