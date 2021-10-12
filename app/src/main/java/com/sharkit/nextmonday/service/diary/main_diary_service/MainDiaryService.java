package com.sharkit.nextmonday.service.diary.main_diary_service;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.diary.MainDiaryAdapter;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class MainDiaryService implements LayoutService {
    private final long date;
    private ExpandableListView listView;
    private TargetDataService dataService;
    private Context context;

    public MainDiaryService(long date) {
        this.date = date;
    }

    @Override
    public LayoutService writeToField() {
        dataService = new TargetDataService(context);
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        listView = root.findViewById(R.id.expandable_list_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        listView.setAdapter(new MainDiaryAdapter(context, dataService.getWeekList(date)));
        return this;
    }

}
