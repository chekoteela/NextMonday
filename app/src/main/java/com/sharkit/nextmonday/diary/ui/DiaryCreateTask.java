package com.sharkit.nextmonday.diary.ui;

import static com.sharkit.nextmonday.configuration.constant.ToastMessage.TASK_IS_ADDED;
import static com.sharkit.nextmonday.diary.constant.DiaryConstant.DATE_FOR_CREATE;
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
import com.sharkit.nextmonday.diary.db.firebase.DiaryTaskRepository;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.service.DiaryCreateTaskService;

import java.util.Calendar;

public class DiaryCreateTask extends Fragment {

    private static final String DATE_FORMAT = "%s.%s";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_create_task, container, false);
        Widget widget = Widget.findByView(view);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(requireArguments().getLong(DATE_FOR_CREATE));

        DiaryCreateTaskService service = new DiaryCreateTaskService(getContext(), widget);

        widget.getSwitch().getRepeat().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                service.showDayForRepeat();
            }
        });

        widget.getSwitch().getTakeTime().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                service.showTimePicker();
            }
        });

        widget.getButton().getAdd().setOnClickListener(v -> new DiaryTaskRepository(requireContext())
                .create(toDiaryTask(service, widget.getTextField(), calendar),
                        String.format(DATE_FORMAT, calendar.get(Calendar.WEEK_OF_YEAR), calendar.get(Calendar.YEAR)))
                .addOnSuccessListener(unused -> {

                    DiaryTaskRepo diaryTaskRepo = DiaryTaskRepo.getInstance(getContext());
                    diaryTaskRepo.create(toDiaryTask(service, widget.getTextField(), calendar));

                    Navigation.findNavController((Activity) requireContext(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_main);
                    TASK_IS_ADDED(requireContext());
                }));

        return view;
    }


}
