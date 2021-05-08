package Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.Target;


public class Calendar extends Fragment {
    final String TAG = "qwerty";
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView CV = root.findViewById(R.id.calendarView);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        TextView date = root.findViewById(R.id.date);
        date.setText( DMonth(calendar.get(java.util.Calendar.DAY_OF_WEEK)) + ", " + calendar.get(java.util.Calendar.DAY_OF_MONTH));

        mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        CV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Week(dayOfMonth,month,year);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_diary);
            }
        });
        return root;
    }
    public void Week(int dayOfMonth, int month, int year){

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar.set(java.util.Calendar.MONTH,month);
        calendar.set(java.util.Calendar.YEAR,year);

        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.MONDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,-1);
        }
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setMonday_numeric(calendar.get(java.util.Calendar.DATE));
        DayOfWeek.setMonday_mouth(DayOfWeek.getMonth());
        DayOfWeek.setMonday_year(calendar.get((java.util.Calendar.YEAR)));
        Target.setYear(DayOfWeek.getMonday_year());
        Target.setMonday_mouth(calendar.get(java.util.Calendar.MONTH));


        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.TUESDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,1);
        }
        DayOfWeek.setTuesday_numeric(calendar.get(java.util.Calendar.DATE));
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setTuesday_month(DayOfWeek.getMonth());
        DayOfWeek.setTuesday_year(calendar.get(java.util.Calendar.YEAR));
        Target.setYear(calendar.get((java.util.Calendar.YEAR)));
        Target.setTuesday_month(calendar.get(java.util.Calendar.MONTH));

        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.WEDNESDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,1);
        }
        DayOfWeek.setWednesday_numeric(calendar.get(java.util.Calendar.DATE));
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setWednesday_month(DayOfWeek.getMonth());
        DayOfWeek.setWednesday_year(calendar.get(java.util.Calendar.YEAR));
        Target.setYear(calendar.get((java.util.Calendar.YEAR)));
        Target.setWednesday_month(calendar.get(java.util.Calendar.MONTH));

        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.THURSDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,1);
        }
        DayOfWeek.setThursday_numeric(calendar.get(java.util.Calendar.DATE));
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setThursday_month(DayOfWeek.getMonth());
        DayOfWeek.setThursday_year(calendar.get(java.util.Calendar.YEAR));
        Target.setYear(calendar.get((java.util.Calendar.YEAR)));
        Target.setThursday_month(calendar.get(java.util.Calendar.MONTH));

        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.FRIDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,1);
        }
        DayOfWeek.setFriday_numeric(calendar.get(java.util.Calendar.DATE));
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setFriday_month(DayOfWeek.getMonth());
        DayOfWeek.setFriday_year(calendar.get(java.util.Calendar.YEAR));
        Target.setYear(calendar.get((java.util.Calendar.YEAR)));
        Target.setFriday_month(calendar.get(java.util.Calendar.MONTH));

        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.SATURDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,1);
        }
        DayOfWeek.setSaturday_numeric(calendar.get(java.util.Calendar.DATE));
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setSaturday_month(DayOfWeek.getMonth());
        DayOfWeek.setSaturday_year(calendar.get(java.util.Calendar.YEAR));
        Target.setYear(calendar.get((java.util.Calendar.YEAR)));
        Target.setSaturday_month(calendar.get(java.util.Calendar.MONTH));

        while (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.SUNDAY){
            calendar.add(java.util.Calendar.DAY_OF_WEEK,1);
        }
        DayOfWeek.setSunday_numeric(calendar.get(java.util.Calendar.DATE));
        Month(calendar.get(java.util.Calendar.MONTH));
        DayOfWeek.setSunday_month(DayOfWeek.getMonth());
        DayOfWeek.setSunday_year(calendar.get(java.util.Calendar.YEAR));
        Target.setYear(calendar.get((java.util.Calendar.YEAR)));
        Target.setSunday_month(calendar.get(java.util.Calendar.MONTH));
    }

    public static String Month(int Mouth) {
        switch (Mouth){
            case 0:
                DayOfWeek.setMonth("Январь");
                break;
            case 1:
                DayOfWeek.setMonth("Февраль");
                break;
            case 2:
                DayOfWeek.setMonth("Март");
                break;
            case 3:
                DayOfWeek.setMonth("Апрель");
                break;
            case 4:
                DayOfWeek.setMonth("Май");
                break;
            case 5:
                DayOfWeek.setMonth("Июнь");
                break;
            case 6:
                DayOfWeek.setMonth("Июль");
                break;
            case 7:
                DayOfWeek.setMonth("Август");
              break;
            case 8:
                DayOfWeek.setMonth("Сентябрь");
                break;
            case 9:
                DayOfWeek.setMonth("Октябрь");
                break;
            case 10:
                DayOfWeek.setMonth("Ноябрь");
                break;
            case 11:
                DayOfWeek.setMonth("Декбрь");
                break;
        }

        return "Wrong Month";

    }

    public static String DMonth(int day) {
        switch (day){
            case 2:
                return "Понедельник";
            case 3:
                return "Вторник";
            case 4:
                return "Среда";
            case 5:
                return "Четверг";
            case 6:
                return "Пятница";
            case 7:
              return "Суббота";
            case 1:
                return "Воскресенье";
        }

        return "Wrong day";

    }

}