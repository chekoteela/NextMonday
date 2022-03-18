package com.sharkit.nextmonday.diary.ui;

import static com.sharkit.nextmonday.configuration.constant.ToastMessage.TASK_IS_ADDED;
import static com.sharkit.nextmonday.diary.constant.DiaryConstant.EMPTY_TIME_FORMAT;
import static com.sharkit.nextmonday.diary.constant.DiaryConstant.OBJECT_FOR_UPDATE;
import static com.sharkit.nextmonday.diary.transformer.DiaryTransformer.toDiaryTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.service.DiaryUpdateTaskService;

public class DiaryUpdateTask extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_update_task, container, false);
        DiaryTask diaryTask = (DiaryTask) requireArguments().getSerializable(OBJECT_FOR_UPDATE);

        Widget widget = Widget.findByView(view);
        DiaryUpdateTaskService service = new DiaryUpdateTaskService(widget, getContext(), diaryTask);

        widget.getSwitch().getTakeTime().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                service.showTimePicker();
            } else {
                widget.getTextView().getRangTime().setText(EMPTY_TIME_FORMAT);
            }
        });

        widget.getSwitch().getRepeat().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                service.showDayForRepeat();
            } else {
                widget.getTextView().getRepeatDays().setText("");
            }
        });

        widget.getButton().getSave().setOnClickListener(v -> {
            DiaryTaskRepo.getInstance(getContext())
                    .update(toDiaryTask(service, widget.getTextField(), diaryTask), diaryTask.getId());
            Navigation.findNavController((Activity) requireContext(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_main);
            TASK_IS_ADDED(requireContext());
        });

        return view;
    }
}
