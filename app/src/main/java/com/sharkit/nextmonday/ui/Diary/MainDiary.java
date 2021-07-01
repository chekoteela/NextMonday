package com.sharkit.nextmonday.ui.Diary;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.Adapters.DiaryList;
import com.sharkit.nextmonday.FirebaseEntity.TargetEntity;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.Days;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Week;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainDiary extends Fragment {
    private ArrayList<Week> dataWeek;
    private ArrayList<Days> day;
    private ExpandableListView expandableListView;
    private TargetData targetData;
    private DiaryList diaryList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_dairy,container, false );
      findView(root);
        targetData = new TargetData(getContext());
        SQLiteDatabase db = targetData.getReadableDatabase();
        targetData.onCreate(db);
        synchronised();
      crateListAdapter();

        return root;
    }

    private void synchronised() {
        TargetEntity entity = new TargetEntity();
        entity.synchronisedToFirestore(getContext());
        TargetData data = new TargetData(getContext());
        data.findFromRepeat();
        data.synchronised();
    }

    private void findView(View root) {
        expandableListView = root.findViewById(R.id.expListView);
    }

    public void crateListAdapter() {
        ArrayList<ArrayList<MyTarget>> allTarget = new ArrayList<>();
        dataWeek = new ArrayList<>();
        day = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DayOfWeek.getMillis());
        ArrayList<MyTarget> targets;

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            targets = new ArrayList<>();
            Week week = new Week();
            Days days = new Days();
            writeDate(days,week,calendar);
            allTarget.add(listTarget(targets,dateFormat.format(calendar.getTimeInMillis())));
            calendar.add(Calendar.DAY_OF_WEEK,1);
        }
        targets = new ArrayList<>();
        Week week = new Week();
        Days days = new Days();
        writeDate(days,week,calendar);
        allTarget.add(listTarget(targets, dateFormat.format(calendar.getTimeInMillis())));

        diaryList = new DiaryList(getContext(), allTarget, dataWeek, day);
        diaryList.notifyDataSetChanged();
        expandableListView.setAdapter(diaryList);



    }


    public void update(){
        diaryList.notifyDataSetChanged();
    }


    private void writeDate(Days days, Week week, Calendar calendar) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        days.setDay(calendar.get(Calendar.DATE));
        days.setMonth(calendar.get(Calendar.MONTH));
        days.setYear(calendar.get(Calendar.YEAR));
        week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
        week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
        week.setNumber(calendar.get(Calendar.DATE));
        week.setBefore(targetData.getCompleteCount(dateFormat.format(calendar.getTimeInMillis())));

        day.add(days);
        dataWeek.add(week);

    }


    private String setNameDay(int i) {
        switch (i){
            case 2:
                return "Пн";
            case 3:
                return "Вт";
            case 4:
                return "Ср";
            case 5:
                return "Чт";
            case 6:
                return "Пт";
            case 7:
                return "Сб";
            case 1:
                return "Нд";
        }
        return "";
    }
    private String setNameMonth(int i){
        switch (i){
            case 0:
                return "Январь";
            case 1:
                return "Февраль";
            case 2:
                return "Март";
            case 3:
                return "Апрель";
            case 4:
                return "Май";
            case 5:
                return "Июнь";
            case 6:
                return "Июль";
            case 7:
                return "Август";
            case 8:
                return "Сеньтябрь";
            case 9:
                return "Октябрь";
            case 10:
                return "Ноябрь";
            case 11:
                return "Декабрь";
        }
        return "";
    }

    private ArrayList<MyTarget> listTarget(ArrayList<MyTarget> targets,String time) {

        targetData.findAllTarget(targets,time);
        return targets;
    }


}
