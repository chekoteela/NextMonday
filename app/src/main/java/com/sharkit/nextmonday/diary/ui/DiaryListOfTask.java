package com.sharkit.nextmonday.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;

public class DiaryListOfTask extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_list_of_task, container, false);

        //list repeated different tasks which we can
        //stop repeat and throw to basket, and then we can return them to repeat
        //we can show information about task, for example: count of complete this kind of task for specific time

        return view;
    }
}


