package com.sharkit.nextmonday.Configuration;

import android.content.Context;

import com.sharkit.nextmonday.Exception.CustomToastException;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.Days;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;

import java.util.ArrayList;
import java.util.Calendar;

public class Validation {
    private ArrayList<ArrayList<MyTarget>> mGroup;
    private ArrayList<Days> mDay;
    private Context mContext;
    public Validation() {
    }


    public Validation(ArrayList<ArrayList<MyTarget>> mGroup,ArrayList<Days> mDay, Context mContext){
        this.mDay=mDay;
        this.mGroup=mGroup;
        this.mContext=mContext;
    }

    public boolean isValidateCreateTarget(int groupPosition, int childPosition){
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.DATE) != mDay.get(groupPosition).getDay() &&
                mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day not time") ||
                mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day with time")){
            try {
                throw new CustomToastException(mContext, "Чтобы изменить задачу подобного типа выберете ее сегодня");
            } catch (CustomToastException e) {
                e.printStackTrace();
            }
            return false;
        }else if (!isValidYesterday(groupPosition)){
            try {
                throw new CustomToastException(mContext, "Нельзя редактирувать задачи задним числом");
            } catch (CustomToastException e) {
                e.printStackTrace();
            }
            return false;
        }else if (mGroup.get(groupPosition).get(childPosition).isStatus()){
            try {
                throw new CustomToastException(mContext, "Нельзя изменить выполненую задачу");
            } catch (CustomToastException e) {
                e.printStackTrace();
            }
            return false;
        }else
        return true;
    }
    public boolean isValidDeleteTodayTarget(int groupPosition, int childPosition){
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.DATE) == mDay.get(groupPosition).getDay() ||
                !mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day not time")) &&
                !mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day with time");
    }
    public boolean isValidYesterday(int groupPosition){
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(DayOfWeek.getMillis());
        instance.set(mDay.get(groupPosition).getYear(),
                mDay.get(groupPosition).getMonth(),
                mDay.get(groupPosition).getDay(), 23,59,59);
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTimeInMillis() > instance.getTimeInMillis()){

            return false;
        }
        Target.setDay(mDay.get(groupPosition).getDay());
        Target.setMonth(mDay.get(groupPosition).getMonth());
        Target.setYear(mDay.get(groupPosition).getYear());
        return true;
    }
    public boolean isNullTime(int groupPosition, int childPosition){
        return mGroup.get(groupPosition).get(childPosition).getRepeat().equals("one not time") ||
                mGroup.get(groupPosition).get(childPosition).getRepeat().equals("select day not time") ||
                mGroup.get(groupPosition).get(childPosition).getRepeat().equals("every day not time");
    }
    public boolean isValidDeleteTarget(int groupPosition, int childPosition) {
        if (mGroup.get(groupPosition).get(childPosition).isStatus()) {
            try {
                throw new CustomToastException(mContext, "Невозможно удалить выполнену задачу");
            } catch (CustomToastException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }


}
