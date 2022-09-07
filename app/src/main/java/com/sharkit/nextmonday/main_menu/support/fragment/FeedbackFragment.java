package com.sharkit.nextmonday.main_menu.support.fragment;

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
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.main_menu.support.adapter.MainFeedbackAdapter;
import com.sharkit.nextmonday.main_menu.support.dialog.CreateFeedbackDialog;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackEntity;
import com.sharkit.nextmonday.main_menu.support.widget.SupportWidget;

import java.util.ArrayList;
import java.util.List;

public class FeedbackFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.support_feedback_list, container, false);
        final SupportWidget.FeedbackWidget widget = SupportWidget.newInstance(view).getFeedbackWidget();
        final User user = new UserSharedPreference(getContext()).get();

        widget.getCreate().setOnClickListener(v -> new CreateFeedbackDialog(getContext()).showDialog());

        user.getRole().getAllActiveFeedbacks(getContext(), user.getId())
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<FeedbackEntity> entities = new ArrayList<>(queryDocumentSnapshots.toObjects(FeedbackEntity.class));
                    widget.getListOfFeedback().setAdapter(new MainFeedbackAdapter(entities, getContext()));
                });

        return view;
    }
}