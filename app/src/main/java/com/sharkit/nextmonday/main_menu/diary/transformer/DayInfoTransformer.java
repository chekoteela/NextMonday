package com.sharkit.nextmonday.main_menu.diary.transformer;

import static com.sharkit.nextmonday.main_menu.NavigationMenu.getContext;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.util.Calendar;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DayInfoTransformer {

    public static String toMonthName(int month) {
        switch (month) {
            case 0:
                return getValue(R.string.text_view_january);
            case 1:
                return getValue(R.string.text_view_february);
            case 2:
                return getValue(R.string.text_view_march);
            case 3:
                return getValue(R.string.text_view_april);
            case 4:
                return getValue(R.string.text_view_may);
            case 5:
                return getValue(R.string.text_view_june);
            case 6:
                return getValue(R.string.text_view_jule);
            case 7:
                return getValue(R.string.text_view_august);
            case 8:
                return getValue(R.string.text_view_september);
            case 9:
                return getValue(R.string.text_view_october);
            case 10:
                return getValue(R.string.text_view_november);
            case 11:
                return getValue(R.string.text_view_december);
            default:
                throw new RuntimeException("Untyped value " + month);
        }
    }

    public static String toDayName(int day) {
        switch (day) {
            case 2:
                return getValue(R.string.text_view_monday);
            case 3:
                return getValue(R.string.text_view_tusday);
            case 4:
                return getValue(R.string.text_view_wednesday);
            case 5:
                return getValue(R.string.text_view_thursday);
            case 6:
                return getValue(R.string.text_view_friday);
            case 7:
                return getValue(R.string.text_view_saturday);
            case 1:
                return getValue(R.string.text_view_sunday);
            default:
                throw new RuntimeException("Untyped value " + day);
        }
    }

    public static String toDayName(DayOfRepeat dayOfRepeat) {
        switch (dayOfRepeat) {
            case MONDAY:
                return getValue(R.string.text_view_monday);
            case TUESDAY:
                return getValue(R.string.text_view_tusday);
            case WEDNESDAY:
                return getValue(R.string.text_view_wednesday);
            case THURSDAY:
                return getValue(R.string.text_view_thursday);
            case FRIDAY:
                return getValue(R.string.text_view_friday);
            case SATURDAY:
                return getValue(R.string.text_view_saturday);
            case SUNDAY:
                return getValue(R.string.text_view_sunday);
            default:
                throw new RuntimeException("Untyped value " + dayOfRepeat);
        }
    }

    public static int toDayOfWeek(DayOfRepeat dayOfRepeat) {
        switch (dayOfRepeat) {
            case MONDAY:
                return Calendar.MONDAY;
            case TUESDAY:
                return Calendar.TUESDAY;
            case WEDNESDAY:
                return Calendar.WEDNESDAY;
            case THURSDAY:
                return Calendar.THURSDAY;
            case FRIDAY:
                return Calendar.FRIDAY;
            case SATURDAY:
                return Calendar.SATURDAY;
            case SUNDAY:
                return Calendar.SUNDAY;
            default:
                throw new RuntimeException("Unsupported type");
        }
    }

    private static String getValue(int idRes) {
        return getContext().getString(idRes);
    }

}
