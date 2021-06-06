package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.sharkit.nextmonday.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.SizeTarget;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Users;

import java.util.ArrayList;
import java.util.Calendar;


import static com.facebook.FacebookSdk.getApplicationContext;


public class MyExpandebleListAdapter extends BaseExpandableListAdapter {
    final String TAG = "qwerty";
      ArrayList<ArrayList<String>> mGroups;
      Context mContext;

       int day[] = {DayOfWeek.getMonday_numeric(), DayOfWeek.getTuesday_numeric(),
      DayOfWeek.getWednesday_numeric(), DayOfWeek.getThursday_numeric(),
      DayOfWeek.getFriday_numeric(), DayOfWeek.getSaturday_numeric(), DayOfWeek.getSunday_numeric()};

       String dayOfWeek[] = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"};

       String month[] = {DayOfWeek.getMonday_mouth(), DayOfWeek.getTuesday_month(),
      DayOfWeek.getWednesday_month(), DayOfWeek.getThursday_month(),
      DayOfWeek.getFriday_month(), DayOfWeek.getSaturday_month(), DayOfWeek.getSunday_month()};

      int[] completeTargetarr = {SizeTarget.getMonCompleteTarget(), SizeTarget.getTusCompleteTarget(),
      SizeTarget.getWedCompleteTarget(), SizeTarget.getThdCompleteTarget(),
      SizeTarget.getFriCompleteTarget(), SizeTarget.getSatCompleteTarget(), SizeTarget.getSunACompleteTarget()};

      int[] percent = {ProgressBar(SizeTarget.getMonCompleteTarget(), SizeTarget.getMonAllTarget()),
      ProgressBar(SizeTarget.getTusCompleteTarget(), SizeTarget.getTusAllTarget()),
      ProgressBar(SizeTarget.getWedCompleteTarget(), SizeTarget.getWedAllTarget()),
      ProgressBar(SizeTarget.getThdCompleteTarget(), SizeTarget.getThdAllTarget()),
      ProgressBar(SizeTarget.getFriCompleteTarget(), SizeTarget.getFriAllTarget()),
      ProgressBar(SizeTarget.getSatCompleteTarget(), SizeTarget.getSatAllTarget()),
      ProgressBar(SizeTarget.getSunACompleteTarget(), SizeTarget.getSunAllTarget())};

      Cursor query;
      ArrayList<String> arrayText, arrayTime, arrayAllTime;
      ArrayList<Boolean> arrayComplete;

      FirebaseAuth mAuth;
      FirebaseDatabase fdb;
      DatabaseReference users;
      DatabaseHelper databaseHelper;
      SQLiteDatabase db;




    public MyExpandebleListAdapter(Context context, ArrayList<ArrayList<String>> groups) {
        mContext = context;
        mGroups = groups;

    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        databaseHelper.onCreate(db);
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_diary, null);
        }
        ImageView plus = convertView.findViewById(R.id.plus);
        TextView num = convertView.findViewById(R.id.num);
        TextView dayWeek = convertView.findViewById(R.id.day);
        TextView dayMonth = convertView.findViewById(R.id.month);
        TextView after = convertView.findViewById(R.id.pisly);
        TextView before = convertView.findViewById(R.id.dop);
        ProgressBar pb = convertView.findViewById(R.id.progressBar123);
        LinearLayout linearLayout = convertView.findViewById(R.id.linear);
        LinearLayout lin = convertView.findViewById(R.id.lin);
        RelativeLayout relativeLayout = convertView.findViewById(R.id.rootParent);

        relativeLayout.post(new Runnable() {
            @Override
            public void run() {
            int h = SizeTarget.getHeight()/7;
                ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(-1,h);
                lin.setLayoutParams(params);
            }
        });


            num.setText(day[groupPosition] + "");
            dayWeek.setText(dayOfWeek[groupPosition]);
            dayMonth.setText(month[groupPosition]);
            after.setText(mGroups.get(groupPosition).size() + "");
            before.setText(completeTargetarr[groupPosition] + "");
            pb.setProgress(percent[groupPosition]);
            linearLayout.setBackgroundResource(R.color.calculator_auto_edittext);

            if (SelectToday(Integer.parseInt(num.getText().toString().trim())) == groupPosition){
                linearLayout.setBackgroundResource(R.color.calculator_color);
            }

        if (isExpanded) {
        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (groupPosition) {
                    case 0:
                   if(isProhibitClick(DayOfWeek.getMonday_numeric(), Target.getMonday_mouth(),DayOfWeek.getMonday_year())) {
                       navController.navigate(R.id.nav_plus_target);
                       Target.setYear(DayOfWeek.getMonday_year());
                       Target.setDay(DayOfWeek.getMonday_numeric());
                       Target.setMonth(Target.getMonday_mouth());
                   }else{
                      Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();

                       return;
                   }
                        break;
                    case 1:
                        if (isProhibitClick(DayOfWeek.getTuesday_numeric(),Target.getTuesday_month(),DayOfWeek.getTuesday_year())){
                        navController.navigate(R.id.nav_plus_target);
                        Target.setYear(DayOfWeek.getTuesday_year());
                        Target.setDay(DayOfWeek.getTuesday_numeric());
                        Target.setMonth(Target.getTuesday_month());
                        }else{
                            Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();
                       return;
                   }
                        break;
                    case 2:
                        if (isProhibitClick(DayOfWeek.getWednesday_numeric(),Target.getWednesday_month(),DayOfWeek.getWednesday_year())) {
                            navController.navigate(R.id.nav_plus_target);
                            Target.setYear(DayOfWeek.getWednesday_year());
                            Target.setDay(DayOfWeek.getWednesday_numeric());
                            Target.setMonth(Target.getWednesday_month());
                        }else {
                            Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case 3:
                        if(isProhibitClick(DayOfWeek.getThursday_numeric(),Target.getThursday_month(),DayOfWeek.getThursday_year())) {
                            navController.navigate(R.id.nav_plus_target);
                            Target.setYear(DayOfWeek.getThursday_year());
                            Target.setDay(DayOfWeek.getThursday_numeric());
                            Target.setMonth(Target.getThursday_month());
                        }else {
                            Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case 4:
                        if (isProhibitClick(DayOfWeek.getFriday_numeric(),Target.getFriday_month(),DayOfWeek.getFriday_year())) {
                            navController.navigate(R.id.nav_plus_target);
                            Target.setYear(DayOfWeek.getFriday_year());
                            Target.setDay(DayOfWeek.getFriday_numeric());
                            Target.setMonth(Target.getFriday_month());
                        }else{
                            Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case 5:
                        if (isProhibitClick(DayOfWeek.getSaturday_numeric(),Target.getSaturday_month(),DayOfWeek.getSaturday_year())) {
                            navController.navigate(R.id.nav_plus_target);
                            Target.setYear(DayOfWeek.getSaturday_year());
                            Target.setDay(DayOfWeek.getSaturday_numeric());
                            Target.setMonth(Target.getSaturday_month());
                        }else {
                            Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case 6:
                        if (isProhibitClick(DayOfWeek.getSunday_numeric(),Target.getSunday_month(),DayOfWeek.getSunday_year())) {
                            navController.navigate(R.id.nav_plus_target);
                            Target.setYear(DayOfWeek.getSunday_year());
                            Target.setDay(DayOfWeek.getSunday_numeric());
                            Target.setMonth(Target.getSunday_month());
                        }else {
                            Toast.makeText(mContext, "Вы не можете задать задачу задним числом", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                }
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lst_view_castom, null);
        }
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        CheckBox completeTarget = convertView.findViewById(R.id.completeTarget);
        TextView textTarget = convertView.findViewById(R.id.textTarget);
        TextView timeTarget = convertView.findViewById(R.id.timeTarget);
        RelativeLayout changeItem = convertView.findViewById(R.id.create_child_item);



        changeItem.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Изменить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Target.setAll_time(arrayAllTime.get(childPosition));
                        navController.navigate(R.id.nav_change_target);
                        return true;
                    }
                });
                menu.add("Удалить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DropTargetFromSQLite(arrayAllTime.get(childPosition));
                        DropTargetFromFirebase(arrayAllTime.get(childPosition));
                        navController.navigate(R.id.nav_diary);
                        return true;
                    }
                });
            }
        });

        completeTarget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChangeTarget(arrayAllTime.get(childPosition), isChecked);

            }
        });

        completeTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_diary);
            }
        });

        if (groupPosition == 0) {
            ListOfDay(DayOfWeek.getMonday_numeric(), Target.getMonday_mouth(), DayOfWeek.getMonday_year());
            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        if (groupPosition == 1) {
            ListOfDay(DayOfWeek.getTuesday_numeric(), Target.getTuesday_month(), DayOfWeek.getTuesday_year());
            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        if (groupPosition == 2) {
            ListOfDay(DayOfWeek.getWednesday_numeric(), Target.getWednesday_month(), DayOfWeek.getWednesday_year());
            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        if (groupPosition == 3) {
            ListOfDay(DayOfWeek.getThursday_numeric(), Target.getThursday_month(), DayOfWeek.getThursday_year());
            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        if (groupPosition == 4) {
            ListOfDay(DayOfWeek.getFriday_numeric(), Target.getFriday_month(), DayOfWeek.getFriday_year());
            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        if (groupPosition == 5) {
            ListOfDay(DayOfWeek.getSaturday_numeric(), Target.getSaturday_month(), DayOfWeek.getSaturday_year());
            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        if (groupPosition == 6) {
            ListOfDay(DayOfWeek.getSunday_numeric(), Target.getSunday_month(), DayOfWeek.getSunday_year());

            for (int i = 0; i < mGroups.get(groupPosition).size(); i++) {
                textTarget.setText(arrayText.get(childPosition));
                timeTarget.setText(arrayTime.get(childPosition));
                completeTarget.setChecked(arrayComplete.get(childPosition));
            }
        }
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    private void DropTargetFromFirebase(String s) {
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        users = fdb.getReference();
        users.child("Users/" + mAuth.getCurrentUser().getUid() + "/Targets/MineTargets/" + s ).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                currentData.setValue(null);
                return Transaction.success(currentData);
            }
            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    private void DropTargetFromSQLite(String s) {
        db = databaseHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + databaseHelper.TABLE + " WHERE " + databaseHelper.COLUMN_TIME + " = '" + s + "'");
    }




    public void ListOfDay(int day, int month, int year) {

        db = databaseHelper.getReadableDatabase();
        arrayText = new ArrayList<>();
        arrayComplete = new ArrayList<>();
        arrayTime = new ArrayList<>();
        arrayAllTime = new ArrayList<>();

        query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " +databaseHelper.COLUMN_ID + " = '" + Users.getUID() + "'", null);



        while (query.moveToNext()) {

            Log.d(TAG, "while " + query.getPosition());
            if (query.getInt(14) == day && query.getInt(13) == month && query.getInt(15) == year) {
                arrayAllTime.add(query.getString(16));
                arrayText.add(query.getString(1));
                if(query.getInt(12) == -1 && query.getInt(11) == -1){
                    arrayTime.add("--:--");
                }else {
                    arrayTime.add(query.getInt(12) + ":" + query.getInt(11));
                }
                if (query.getInt(2) == 0) {
                    arrayComplete.add(false);
                } else {
                    arrayComplete.add(true);
                }
            }
        }
        Log.d(TAG, "after while");

    }

    public void ChangeTarget(String time, boolean isChecked) {
        db = databaseHelper.getReadableDatabase();
        if (isChecked) {
            db.execSQL("UPDATE " + databaseHelper.TABLE + " SET " + databaseHelper.COLUMN_COMPLETE + "= 1  WHERE " + databaseHelper.COLUMN_TIME + " = '" + time + "'");
        } else {
            db.execSQL("UPDATE " + databaseHelper.TABLE + " SET " + databaseHelper.COLUMN_COMPLETE + "= 0  WHERE " + databaseHelper.COLUMN_TIME + " = '" + time + "'");
        }

    }

    public int ProgressBar(int before, int after) {
        int a;
        if (before != 0) {
            a = (100 * before) / after;
        }else {
            a = 0;
        }
        return a;
    }

    public int  SelectToday (int day){
        int position = -1;
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){
            position = 0;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){
            position = 1;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){
            position = 2;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){

            position = 3;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){
            position = 4;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){
            position = 5;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY &&
                day == calendar.get(Calendar.DAY_OF_MONTH)){
            position = 6;
        }

        return position;

    }

   public boolean isProhibitClick(int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, year);
        calendar1.set(Calendar.MONTH, month);
        calendar1.set(Calendar.DAY_OF_MONTH, day);
        if(calendar.getTimeInMillis() > calendar1.getTimeInMillis()){
            return false;
        }else
        return true;
    }


}









