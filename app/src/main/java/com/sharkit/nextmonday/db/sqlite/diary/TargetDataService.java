package com.sharkit.nextmonday.db.sqlite.diary;

import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.APRIL;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.AUGUST;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.DECEMBER;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.FEBRUARY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.FRIDAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.JANUARY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.JULY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.JUNE;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.MARCH;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.MAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.MONDAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.NOVEMBER;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.OCTOBER;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.SATURDAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.SEPTEMBER;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.SUNDAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.THURSDAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.TUESDAY;
import static com.sharkit.nextmonday.configuration.constant.DayAndMonth.WEDNESDAY;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sharkit.nextmonday.entity.diary.DayTargets;
import com.sharkit.nextmonday.entity.diary.ParentItemData;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
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
    public ArrayList<DayTargets> getWeekList(long date) {
        ArrayList<DayTargets> targets = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        for (int i = 0; i < 7; i++) {
            DayTargets dayTargets = new DayTargets();
            ParentItemData parentItemData = new ParentItemData();
            parentItemData.setDay(getNameOfDay(calendar.get(Calendar.DAY_OF_WEEK)));
            parentItemData.setMonth(getNameOfMonth(calendar.get(Calendar.MONTH)));
            parentItemData.setNumber(calendar.get(Calendar.DATE));
            parentItemData.setDate(calendar.getTimeInMillis());
            parentItemData.setCompleteTargets(targetData.getCompleteTarget(new SimpleDateFormat("dd.MM.yyyy")
                    .format(calendar.getTimeInMillis())));
            dayTargets.setTargetDTOs(targetData.findAllByDate(new SimpleDateFormat("dd.MM.yyyy")
                    .format(calendar.getTimeInMillis())));
            parentItemData.setAllTargets(dayTargets.getTargetDTOs().size());
            dayTargets.setParentItemData(parentItemData);
            targets.add(dayTargets);
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return targets;
    }

    public String getNameOfMonth(int month) {
        switch (month) {
            case 0:
                return JANUARY;
            case 1:
                return FEBRUARY;
            case 2:
                return MARCH;
            case 3:
                return APRIL;
            case 4:
                return MAY;
            case 5:
                return JULY;
            case 6:
                return JUNE;
            case 7:
                return AUGUST;
            case 8:
                return SEPTEMBER;
            case 9:
                return OCTOBER;
            case 10:
                return NOVEMBER;
            case 11:
                return DECEMBER;
        }
        return null;
    }
    public String getNameOfDay(int day) {
        switch (day) {
            case 1:
                return SUNDAY;
            case 2:
                return MONDAY;
            case 3:
                return TUESDAY;
            case 4:
                return WEDNESDAY;
            case 5:
                return THURSDAY;
            case 6:
                return FRIDAY;
            case 7:
                return SATURDAY;
        }
        return null;
    }

    public void create(TargetDiary targetDiary) {
        targetData.create(targetDiary);
    }

    public void delete(long date) {
        targetData.delete(date);
    }

    public void setCheckedTarget(long date, boolean isChecked) {
        targetData.setCheckedTarget(date, isChecked);
    }

    public TargetDateForAlarmDTO getRepeatForAlarmDTO(long date) {
        return targetData.getRepeatForAlarmDTO(date);
    }

    public TargetDiary findByDate(long date) {
        return targetData.findByDate(date);
    }

    public void update(TargetDiary targetDiary, long date) {
        targetData.update(targetDiary, date);
    }
}
