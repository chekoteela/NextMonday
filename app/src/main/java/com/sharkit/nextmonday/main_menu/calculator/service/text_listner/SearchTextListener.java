package com.sharkit.nextmonday.main_menu.calculator.service.text_listner;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ExpandableListView;

import com.google.android.material.tabs.TabLayout;
import com.sharkit.nextmonday.main_menu.calculator.enums.SearchType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchTextListener implements TextWatcher {

    private final ExpandableListView foodList;
    private final Context context;
    private final TabLayout tabLayout;

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        SearchType.getById(this.tabLayout.getSelectedTabPosition()).search(s.toString(), this.context, this.foodList);
    }
}
