package com.sharkit.nextmonday.main_menu.calculator.enums;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.configuration.exception.UnsupportedValueException;
import com.sharkit.nextmonday.main_menu.calculator.enums.action.CalculatorCalendarTypeAction;
import com.sharkit.nextmonday.main_menu.calculator.service.calendar_type.FutureCalendarService;
import com.sharkit.nextmonday.main_menu.calculator.service.calendar_type.PastCalendarService;
import com.sharkit.nextmonday.main_menu.calculator.service.calendar_type.PresentCalendarService;

public enum CalculatorCalendarType implements CalculatorCalendarTypeAction {

    PAST,
    PRESENT,
    FUTURE;

    @Override
    public Task<QuerySnapshot> findRation(final String date, final Context context) {
        return this.getService().findRation(date, context);
    }

    private CalculatorCalendarTypeAction getService() {
        switch (this) {
            case PAST:
                return new PastCalendarService();
            case FUTURE:
                return new FutureCalendarService();
            case PRESENT:
                return new PresentCalendarService();
            default:
                throw new UnsupportedValueException(this.name());
        }
    }
}
