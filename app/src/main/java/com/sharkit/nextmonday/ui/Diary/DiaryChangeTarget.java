package com.sharkit.nextmonday.ui.Diary;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_CHANGE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;

import org.jetbrains.annotations.NotNull;

public class DiaryChangeTarget extends Fragment {
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_four, container, false);
        return root;
    }
}
