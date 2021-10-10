package com.sharkit.nextmonday.service.diary.main_diary_service;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_CHANGE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.diary.ParentItemData;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ParentService implements LayoutService{
    private final ParentItemData parentItemData;
    private ProgressBar progressBar;
    private TextView day, number, month, before, after;
    private ImageView create;
    private Context context;

    public ParentService(ParentItemData parentItemData) {
        this.parentItemData = parentItemData;
    }

    @Override
    public void writeToField() {
        day.setText(parentItemData.getDay());
        number.setText(String.valueOf(parentItemData.getNumber()));
        month.setText(parentItemData.getMonth());
        before.setText(String.valueOf(parentItemData.getCompleteTargets()));
        after.setText(String.valueOf(parentItemData.getAllTargets()));
        progressBar.setProgress(getProgress());
    }

    @Override
    public void findById(View root) {
        context = root.getContext();
        day = root.findViewById(R.id.day_xml);
        number = root.findViewById(R.id.num_xml);
        month = root.findViewById(R.id.month_xml);
        before = root.findViewById(R.id.before_xml);
        after = root.findViewById(R.id.after_xml);
        progressBar = root.findViewById(R.id.progress_bar_xml);
        create = root.findViewById(R.id.plus_xml);
    }

    @Override
    public void setAdaptive() {

    }

    @Override
    public void activity() {
        create.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong(DATE_FOR_CHANGE, parentItemData.getDate());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_plus_target, bundle);
        });
    }

    private int getProgress() {
        if (parentItemData.getAllTargets() == 0) {
            return 0;
        } else {
            return 100 * parentItemData.getCompleteTargets() / parentItemData.getAllTargets();
        }
    }
}
