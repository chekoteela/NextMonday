package com.sharkit.nextmonday.ui.Calculator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;

import java.util.Calendar;


public class CalculatorCalendar extends Fragment {

    final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.calculator_calendar, container, false);
        CalendarView calendarView = root.findViewById(R.id.calendar);
        BottomNavigationView bar = root.findViewById(R.id.bar);

        MenuItem item = bar.getMenu().findItem(R.id.calendar);
        item.setIcon(R.drawable.calendar_selected);
        Calendar calendar = Calendar.getInstance();


        Log.d(TAG, bar.getSelectedItemId()+"");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.YEAR,year);

                DayOfWeek.setMillis(calendar.getTimeInMillis());
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_cal_ration);
            }
        });
        return root;
    }
}