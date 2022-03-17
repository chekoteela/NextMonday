package com.sharkit.nextmonday.diary.ui;

import static com.sharkit.nextmonday.diary.constant.DiaryConstant.OBJECT_FOR_UPDATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.service.DiaryUpdateTaskService;

public class DiaryUpdateTask extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_update_task, container, false);
        DiaryTask diaryTask = (DiaryTask) requireArguments().getSerializable(OBJECT_FOR_UPDATE);

        Widget widget = Widget.findByView(view);
        DiaryUpdateTaskService service = new DiaryUpdateTaskService(widget, getContext());
        service.writeFields(diaryTask);

        return view;
    }
}
