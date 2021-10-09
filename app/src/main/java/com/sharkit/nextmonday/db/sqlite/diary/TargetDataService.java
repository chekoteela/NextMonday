package com.sharkit.nextmonday.db.sqlite.diary;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sharkit.nextmonday.entity.diary.TargetDiary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TargetDataService implements TargetServiceMethod{
    private final TargetData targetData;
    public TargetDataService(Context context) {
        targetData = new TargetData(context);
        targetData.onCreate(targetData.getReadableDatabase());
    }

    @SuppressLint("SimpleDateFormat")
    public ArrayList<ArrayList<TargetDiary>> getAllList() {
        ArrayList<ArrayList<TargetDiary>> weekList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        for (int i = 0; i < 7; i++) {
            weekList.add(targetData.
                    findAllByDate(new SimpleDateFormat("dd.MM.yyyy")
                            .format(calendar.getTimeInMillis())));
            calendar.add(Calendar.DAY_OF_WEEK,1);
        }
        return weekList;
    }
}
