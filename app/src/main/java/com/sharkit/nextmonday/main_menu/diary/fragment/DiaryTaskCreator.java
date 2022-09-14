package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_DAY_OF_WEEK;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTaskDTO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogOfRepeaters;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogTimePicker;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.transformer.ByteArrayTransformer;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

public class DiaryTaskCreator extends Fragment {

    private WidgetContainer.TaskCreatorWidget widget;
    private Calendar calendar;

    private final DiaryTask diaryTask = new DiaryTask();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_create_task, container, false);

        this.calendar = Calendar.getInstance();
        this.widget = WidgetContainer.newInstance(view).getTaskCreatorWidget();
        this.widget.getCreate().setOnClickListener(v -> this.createTask());
        this.widget.getTakeTime().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogTimePicker(this.requireContext(), this.diaryTask, this.widget.getTakeTime(), this.calendar)
                .showIfChecked(isChecked));
        this.widget.getRepeat().setOnCheckedChangeListener((buttonView, isChecked) -> new DialogOfRepeaters(this.requireContext(), this.diaryTask).showIfChecked(isChecked));

        this.calendar.setTimeInMillis(this.requireArguments().getLong(DIARY_DAY_OF_WEEK));


        return view;
    }

    private void createTask() {
        this.diaryTask.setDescription(this.widget.getDescription().getText().toString());
        this.diaryTask.setName(this.widget.getNameOfTask().getText().toString());
        this.diaryTask.setDate(DateFormat.getDateInstance().format(this.calendar.getTime()));
        this.diaryTask.setGroupId(UUID.randomUUID().toString());


        NextMondayDatabase.getInstance(this.getContext()).dairyTaskDAO().create(toDiaryTaskDTO(this.diaryTask));
        this.moveToMainMenu();
    }

    private void moveToMainMenu(){
        Navigation.findNavController(this.requireActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_main);
    }
}