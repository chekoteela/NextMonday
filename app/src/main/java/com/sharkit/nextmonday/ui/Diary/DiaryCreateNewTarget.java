package com.sharkit.nextmonday.ui.Diary;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_CHANGE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.diary.create_new_target_service.CreateNewTargetService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DiaryCreateNewTarget extends Fragment {

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_two, container, false);
        CreateNewTargetService service = new CreateNewTargetService(requireArguments().getLong(DATE_FOR_CHANGE));
        service.findById(root);
        service.writeToField();
        service.activity();
        service.setAdaptive();
        return root;
    }
}
