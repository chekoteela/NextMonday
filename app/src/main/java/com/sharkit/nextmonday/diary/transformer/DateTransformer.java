package com.sharkit.nextmonday.diary.transformer;

import android.content.Context;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.diary.enums.DayOfAlarm;

public class DateTransformer {

    public static String toMonthName(Context context, int month) {
        switch (month) {
            case 0:
                return getValue(context, R.string.text_view_january);
            case 1:
                return getValue(context, R.string.text_view_february);
            case 2:
                return getValue(context, R.string.text_view_march);
            case 3:
                return getValue(context, R.string.text_view_april);
            case 4:
                return getValue(context, R.string.text_view_may);
            case 5:
                return getValue(context, R.string.text_view_june);
            case 6:
                return getValue(context, R.string.text_view_jule);
            case 7:
                return getValue(context, R.string.text_view_august);
            case 8:
                return getValue(context, R.string.text_view_september);
            case 9:
                return getValue(context, R.string.text_view_october);
            case 10:
                return getValue(context, R.string.text_view_november);
            case 11:
                return getValue(context, R.string.text_view_december);
            default: throw new RuntimeException("Untyped value " + month);
        }
    }

    public static String toDayName(Context context, int day){
        switch (day) {
            case 2:
                return getValue(context, R.string.text_view_monday);
            case 3:
                return getValue(context, R.string.text_view_tusday);
            case 4:
                return getValue(context, R.string.text_view_wednesday);
            case 5:
                return getValue(context, R.string.text_view_thursday);
            case 6:
                return getValue(context, R.string.text_view_friday);
            case 7:
                return getValue(context, R.string.text_view_saturday);
            case 1:
                return getValue(context, R.string.text_view_sunday);
            default: throw new RuntimeException("Untyped value " + day);
        }
    }
    public static String toDayName(Context context, DayOfAlarm dayOfAlarm){
        switch (dayOfAlarm) {
            case MONDAY:
                return getValue(context, R.string.text_view_monday);
            case TUESDAY:
                return getValue(context, R.string.text_view_tusday);
            case WEDNESDAY:
                return getValue(context, R.string.text_view_wednesday);
            case THURSDAY:
                return getValue(context, R.string.text_view_thursday);
            case FRIDAY:
                return getValue(context, R.string.text_view_friday);
            case SATURDAY:
                return getValue(context, R.string.text_view_saturday);
            case SUNDAY:
                return getValue(context, R.string.text_view_sunday);
            default: throw new RuntimeException("Untyped value " + dayOfAlarm);
        }
    }

    private static String getValue(Context context, int idRes) {
        return context.getString(idRes);
    }
}
