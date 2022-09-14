package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_TASK_FOR_CHANGE;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DayInfoTransformer.toDayName;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DiaryModifyTask extends Fragment {

    private WidgetContainer.DiaryUpdateTaskWidget widget;

    private static final String TAG = DiaryModifyTask.class.getCanonicalName();


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_update_task, container, false);
        final DiaryTask diaryTask = (DiaryTask) this.requireArguments().getSerializable(DIARY_TASK_FOR_CHANGE);
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(diaryTask.getTimeForRepeat());

        this.widget = WidgetContainer.newInstance(view).getDiaryUpdateTaskWidget();
        this.widget.getNameOfTask().setText(diaryTask.getName());
        this.widget.getDescription().setText(diaryTask.getDescription());
        this.widget.getListOfDays().setText(this.getListDays(Optional.ofNullable(diaryTask.getRepeats()).orElse(new ArrayList<>())));
        this.widget.getTakeTime().setChecked(diaryTask.getAlarm());

        if (diaryTask.getRepeats() != null) this.widget.getRepeat().setChecked(Boolean.TRUE);
        if (Boolean.TRUE.equals(diaryTask.getAlarm())) this.widget.getCurrentAlarm().setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()));

        Log.i(TAG, "Successful initialize variables");

        this.widget.getTakeTime().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogTimePicker(this.getContext(), diaryTask, this.widget.getTakeTime(), calendar).showIfChecked(isChecked));
        this.widget.getRepeat().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogOfRepeaters(this.getContext(), diaryTask).showIfChecked(isChecked));
        this.widget.getSave().setOnClickListener(v -> this.save(diaryTask, calendar));
        return view;
    }

    private void save(final DiaryTask diaryTask, final Calendar calendar) {
        diaryTask.setDescription(this.widget.getDescription().getText().toString());
        diaryTask.setName(this.widget.getNameOfTask().getText().toString());
        diaryTask.setDate(DateFormat.getDateInstance().format(calendar.getTime()));

        Log.i(TAG, String.format("Modify task : %s", diaryTask));

        new DialogChangeTask().showDialogUpdate(this.getContext(), diaryTask);
    }

    private String getListDays(final List<DayOfRepeat> repeats) {
        return repeats.stream()
                .map(dayOfRepeat -> toDayName(this.getContext(), dayOfRepeat))
                .collect(Collectors.joining(", "));
    }
}
