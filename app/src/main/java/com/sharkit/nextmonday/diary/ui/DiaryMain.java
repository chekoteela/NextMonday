package com.sharkit.nextmonday.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.adapter.DiaryMainListAdapter;

public class DiaryMain extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_main, container, false);
        Widget widget = Widget.findByView(view);

        widget.getExpandableList().getExpandableListView().setAdapter(new DiaryMainListAdapter(getContext()));
        return view;
    }
}
