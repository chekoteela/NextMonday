package com.sharkit.nextmonday.main_menu.calculator.enums.action;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface CalculatorCalendarTypeAction {

    Task<QuerySnapshot> findRation(String date, Context context);
}
