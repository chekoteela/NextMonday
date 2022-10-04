package com.sharkit.nextmonday.main_menu.calculator.enums.action;

import android.content.Context;
import android.widget.ExpandableListView;

public interface CalculatorSearchTypeAction {

    void search(String tag, Context context, final ExpandableListView foodList);
}
