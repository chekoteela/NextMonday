package com.sharkit.nextmonday.service.diary.main_diary_service;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.CHILD_ITEM_TARGET;
import static com.sharkit.nextmonday.configuration.constant.MenuItemName.CHANGE;
import static com.sharkit.nextmonday.configuration.constant.MenuItemName.DELETE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChildService implements LayoutService {
    private Context context;
    private CheckBox checkBox;
    private TextView text, time;
    private RelativeLayout childItem;
    private final ChildItemTargetDTO targetDiary;

    public ChildService(ChildItemTargetDTO targetDiary) {
        this.targetDiary = targetDiary;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService writeToField() {
        text.setText(targetDiary.getText());
        checkBox.setChecked(targetDiary.isStatus());
        if (targetDiary.isAlarm()) {
            time.setText(new SimpleDateFormat("HH:mm").format(targetDiary.getDate()));
        } else {
            time.setText("--:--");
        }
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        checkBox = root.findViewById(R.id.completeTarget);
        text = root.findViewById(R.id.textTarget);
        time = root.findViewById(R.id.timeTarget);
        childItem = root.findViewById(R.id.child_item_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        TargetDataService service = new TargetDataService(context);
        childItem.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(CHANGE).setOnMenuItemClickListener(item -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CHILD_ITEM_TARGET, targetDiary);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_change_target, bundle);
                return true;
            });
            menu.add(DELETE).setOnMenuItemClickListener(item -> {
                service.delete(targetDiary.getDate());
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
                return true;
            });
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> service.setCheckedTarget(targetDiary.getDate(), isChecked));
        return this;
    }
}
