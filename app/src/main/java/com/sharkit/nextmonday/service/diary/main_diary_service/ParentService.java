package com.sharkit.nextmonday.service.diary.main_diary_service;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_CHANGE;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.ERROR_PAST_DATE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adaptive.linear.LinearAdaptive;
import com.sharkit.nextmonday.entity.diary.ParentItemData;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Calendar;

public class ParentService implements LayoutService {
    private final ParentItemData parentItemData;
    private ProgressBar progressBar;
    private TextView day, number, month, before, after;
    private ImageView create;
    private Context context;
    private RelativeLayout parentLayout;

    public ParentService(ParentItemData parentItemData) {
        this.parentItemData = parentItemData;
    }

    @Override
    public LayoutService writeToField() {
        day.setText(parentItemData.getDay());
        number.setText(String.valueOf(parentItemData.getNumber()));
        month.setText(parentItemData.getMonth());
        before.setText(String.valueOf(parentItemData.getCompleteTargets()));
        after.setText(String.valueOf(parentItemData.getAllTargets()));
        progressBar.setProgress(getProgress());
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        parentLayout = root.findViewById(R.id.parent_xml);
        day = root.findViewById(R.id.day_xml);
        number = root.findViewById(R.id.num_xml);
        month = root.findViewById(R.id.month_xml);
        before = root.findViewById(R.id.before_xml);
        after = root.findViewById(R.id.after_xml);
        progressBar = root.findViewById(R.id.progress_bar_xml);
        create = root.findViewById(R.id.plus_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        if (Calendar.getInstance().get(Calendar.DATE) == parentItemData.getNumber()) {
            parentLayout.setBackgroundColor(context.getColor(R.color.black));           //задасиш нормальний колір для цього елемента, щоб він заливав весь елемент, а не тільки робив чорну рамку, зробиш, і вадали цей комент
        }
        new LinearAdaptive(context)
                .setWidget(day)
                .build();

        return this;
    }

    @Override
    public LayoutService activity() {
        create.setOnClickListener(v -> {
            if (Calendar.getInstance().get(Calendar.DATE) > parentItemData.getNumber()) {
                Toast.makeText(context, ERROR_PAST_DATE, Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putLong(DATE_FOR_CHANGE, parentItemData.getDate());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_plus_target, bundle);
        });
        return this;
    }

    private int getProgress() {
        if (parentItemData.getAllTargets() == 0) {
            return 0;
        } else {
            return 100 * parentItemData.getCompleteTargets() / parentItemData.getAllTargets();
        }
    }
}
