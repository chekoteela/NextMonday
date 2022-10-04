package com.sharkit.nextmonday.main_menu.calculator.enums;

import android.content.Context;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.configuration.exception.UnsupportedValueException;
import com.sharkit.nextmonday.main_menu.calculator.enums.action.CalculatorSearchTypeAction;
import com.sharkit.nextmonday.main_menu.calculator.service.search.GeneralSearchService;

public enum SearchType implements CalculatorSearchTypeAction {

    GENERAL,
    FAVORITE,
    LAST_ADDED;

    @Override
    public void search(final String tag, final Context context, final ExpandableListView foodList) {
        this.getServiceAction().search(tag, context, foodList);
    }

    private CalculatorSearchTypeAction getServiceAction() {
        switch (this) {
            case GENERAL:
                return new GeneralSearchService();
            default: throw new UnsupportedValueException(this.name());
        }
    }
}
