package com.sharkit.nextmonday.ui.Diary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.sharkit.nextmonday.Adapters.DiaryList;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Week;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class MainDiary extends Fragment {
    private final String TAG = "qwerty";
    private ArrayList<MyTarget> targets;
    private ArrayList<Week> dataWeek;
    private ArrayList<ArrayList<Week>> allDataWeek;
    private ArrayList<ArrayList<MyTarget>> allTarget;
    private ExpandableListView expandableListView;
    private  Calendar calendar;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_dairy,container, false );
      findView(root);
      crateListAdapter();

        return root;
    }

    private void findView(View root) {
        expandableListView = root.findViewById(R.id.expListView);
    }

    private void crateListAdapter() {
        allTarget = new ArrayList<>();
        dataWeek = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            targets = new ArrayList<>();
            Week week = new Week();
            dataWeek.add(listData(week, i));
            allTarget.add(listTarget(targets));
        }
        DiaryList diaryList = new DiaryList(getContext(), allTarget, dataWeek);
        expandableListView.setAdapter(diaryList);

    }

    private Week listData(Week week, int i) {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DayOfWeek.getMillis());

        switch (i){
            case 0:
               while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
                   calendar.add(Calendar.DAY_OF_WEEK,-1);
               }
               week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
               week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
               week.setNumber(calendar.get(Calendar.DATE));

               break;
            case 1:
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY){
                    calendar.add(Calendar.DAY_OF_WEEK,1);
                }
                week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
                week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
                week.setNumber(calendar.get(Calendar.DATE));

                break;
            case 2:
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY){
                    calendar.add(Calendar.DAY_OF_WEEK,1);
                }
                week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
                week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
                week.setNumber(calendar.get(Calendar.DATE));

                break;
            case 3:
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY){
                    calendar.add(Calendar.DAY_OF_WEEK,1);
                }
                week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
                week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
                week.setNumber(calendar.get(Calendar.DATE));

                break;
            case 4:
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY){
                    calendar.add(Calendar.DAY_OF_WEEK,1);
                }
                week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
                week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
                week.setNumber(calendar.get(Calendar.DATE));

                break;
            case 5:
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
                    calendar.add(Calendar.DAY_OF_WEEK,1);
                }
                week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
                week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
                week.setNumber(calendar.get(Calendar.DATE));

                break;
            case 6:
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
                    calendar.add(Calendar.DAY_OF_WEEK,1);
                }
                week.setMonth(setNameMonth(calendar.get(Calendar.MONTH)));
                week.setDay(setNameDay(calendar.get(Calendar.DAY_OF_WEEK)));
                week.setNumber(calendar.get(Calendar.DATE));

                break;

        }

        return week;
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

    private ArrayList<MyTarget> listTarget(ArrayList<MyTarget> targets) {
        
        return targets;
    }


}
