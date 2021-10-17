package com.sharkit.nextmonday.ui.diary;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.CHILD_ITEM_TARGET;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.service.diary.update_target_service.UpdateTargetService;

import org.jetbrains.annotations.NotNull;

public class DiaryUpdateTarget extends Fragment {
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_four, container, false);
        UpdateTargetService service = new UpdateTargetService((ChildItemTargetDTO) requireArguments().getSerializable(CHILD_ITEM_TARGET));
        service.findById(root)
                .setAdaptive()
                .writeToField()
                .activity();
        return root;
    }
}
