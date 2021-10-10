package com.sharkit.nextmonday.ui.Diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.diary.MainDiaryAdapter;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;

import org.jetbrains.annotations.NotNull;

public class MainDiary extends Fragment {
    private ExpandableListView listView;
    private TargetDataService dataService;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy, container, false);
        findView(root);
        dataService = new TargetDataService(getContext());
        setAdapter();
        return root;
    }

    private void setAdapter() {
        listView.setAdapter(new MainDiaryAdapter(getContext(), dataService.getWeekList()));
    }

    private void findView(View root) {
        listView = root.findViewById(R.id.expandable_list_xml);
    }
}
