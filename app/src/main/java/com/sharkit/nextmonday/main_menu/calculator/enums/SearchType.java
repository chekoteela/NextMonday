package com.sharkit.nextmonday.main_menu.calculator.enums;

import android.content.Context;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.configuration.exception.UnsupportedIdException;
import com.sharkit.nextmonday.configuration.exception.UnsupportedValueException;
import com.sharkit.nextmonday.main_menu.calculator.enums.action.CalculatorSearchTypeAction;
import com.sharkit.nextmonday.main_menu.calculator.service.search.BarCodeSearchService;
import com.sharkit.nextmonday.main_menu.calculator.service.search.FavoriteSearchService;
import com.sharkit.nextmonday.main_menu.calculator.service.search.GeneralSearchService;
import com.sharkit.nextmonday.main_menu.calculator.service.search.LastAddedSearchService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchType implements CalculatorSearchTypeAction {

    LAST_ADDED(0),
    GENERAL(1),
    FAVORITE(2),
    BY_BAR_CODE(3);

    @Getter
    private final Integer id;

    @Override
    public void search(final String tag, final Context context, final ExpandableListView foodList) {
        this.getServiceAction().search(tag, context, foodList);
    }

    @Override
    public void findDefault(final Context context, final ExpandableListView foodList) {
        this.getServiceAction().findDefault(context, foodList);
    }

    public static SearchType getById(final Integer id) {
        switch (id) {
            case 0:
                return SearchType.LAST_ADDED;
            case 1:
                return SearchType.GENERAL;
            case 2:
                return SearchType.FAVORITE;
            case 3:
                return SearchType.BY_BAR_CODE;
            default:
                throw new UnsupportedIdException(id);
        }
    }

    private CalculatorSearchTypeAction getServiceAction() {
        switch (this) {
            case LAST_ADDED:
                return new LastAddedSearchService();
            case GENERAL:
                return new GeneralSearchService();
            case FAVORITE:
                return new FavoriteSearchService();
            case BY_BAR_CODE:
                return new BarCodeSearchService();
            default:
                throw new UnsupportedValueException(this.name());
        }
    }
}
