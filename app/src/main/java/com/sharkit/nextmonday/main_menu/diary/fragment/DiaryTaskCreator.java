package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_CALENDAR;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTaskDTO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogOfRepeaters;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogTimePicker;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;

import java.text.DateFormat;
import java.util.Calendar;

public class DiaryTaskCreator extends Fragment {

    private WidgetContainer.TaskCreatorWidget widget;
    private Calendar calendar;

    private final DiaryTask diaryTask = new DiaryTask();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_create_task, container, false);

        this.calendar = Calendar.getInstance();
        this.widget = WidgetContainer.newInstance(view).getTaskCreatorWidget();
        this.widget.getCreate().setOnClickListener(v -> createTask());
        this.widget.getTakeTime().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogTimePicker(requireContext(), diaryTask, widget.getTakeTime(), calendar).showIfChecked(isChecked));
        this.widget.getRepeat().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogOfRepeaters(requireContext(), diaryTask).showIfChecked(isChecked));

        calendar.setTimeInMillis(requireArguments().getLong(DIARY_CALENDAR));

        return view;
    }

    private void createTask() {
        diaryTask.setDescription(widget.getDescription().getText().toString());
        diaryTask.setName(widget.getNameOfTask().getText().toString());
        diaryTask.setDate(DateFormat.getDateInstance().format(calendar.getTime()));
        diaryTask.setTimeForRepeat(calendar.getTimeInMillis());

        NextMondayDatabase.getInstance().dairyTaskDAO().create(toDiaryTaskDTO(diaryTask));
    }

}