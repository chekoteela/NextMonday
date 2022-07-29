package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_TASK_FOR_CHANGE;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogChangeTask;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogOfRepeaters;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogTimePicker;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;
import com.sharkit.nextmonday.main_menu.diary.transformer.DayInfoTransformer;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DiaryModifyTask extends Fragment {

    private WidgetContainer.DiaryUpdateTaskWidget widget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_update_task, container, false);
        final DiaryTask diaryTask = (DiaryTask) requireArguments().getSerializable(DIARY_TASK_FOR_CHANGE);
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(diaryTask.getTimeForRepeat());

        this.widget = WidgetContainer.newInstance(view).getDiaryUpdateTaskWidget();
        this.widget.getNameOfTask().setText(diaryTask.getName());
        this.widget.getDescription().setText(diaryTask.getDescription());
        this.widget.getListOfDays().setText(getListDays(Optional.ofNullable(diaryTask.getRepeats()).orElse(new ArrayList<>())));
        this.widget.getTakeTime().setChecked(diaryTask.getAlarm());

        if (diaryTask.getRepeats() != null) this.widget.getRepeat().setChecked(Boolean.TRUE);
        if (Boolean.TRUE.equals(diaryTask.getAlarm())) this.widget.getCurrentAlarm().setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));

        this.widget.getTakeTime().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogTimePicker(getContext(), diaryTask, widget.getTakeTime(), calendar).showIfChecked(isChecked));
        this.widget.getRepeat().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogOfRepeaters(getContext(), diaryTask).showIfChecked(isChecked));
        this.widget.getSave().setOnClickListener(v -> save(diaryTask, calendar));
        return view;
    }

    private void save(DiaryTask diaryTask, Calendar calendar) {
        diaryTask.setDescription(this.widget.getDescription().getText().toString());
        diaryTask.setName(this.widget.getNameOfTask().getText().toString());
        diaryTask.setDate(DateFormat.getDateInstance().format(calendar.getTime()));
        new DialogChangeTask().showDialogUpdate(getContext(), diaryTask);
    }

    private String getListDays(List<DayOfRepeat> repeats) {
        return repeats.stream().map(DayInfoTransformer::toDayName).collect(Collectors.joining(", "));
    }
}
