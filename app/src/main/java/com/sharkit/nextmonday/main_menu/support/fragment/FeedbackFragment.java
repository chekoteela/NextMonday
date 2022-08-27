package com.sharkit.nextmonday.main_menu.support.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.support.widget.SupportWidget;

public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.support_feedback, container, false);
        final SupportWidget.FeedbackWidget widget = SupportWidget.newInstance(view).getFeedbackWidget();
        return view;
    }
}
