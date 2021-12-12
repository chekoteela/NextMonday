package com.sharkit.nextmonday.service.diary.main_diary_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.diary.MainDiaryAdapter;
import com.sharkit.nextmonday.db.firestore.diary.DiaryFirestore;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService activity() {
        dataService.getAlarm(new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()));
        synchronizedDB();
        listView.setAdapter(new MainDiaryAdapter(context, dataService.getWeekList(date)));
        return this;
    }

    private void synchronizedDB() {
        TargetDataService service = new TargetDataService(context);
        DiaryFirestore diaryFirestore = new DiaryFirestore();
        diaryFirestore.synchronizedDB(service);
        service.synchronizedDB();
    }
}
