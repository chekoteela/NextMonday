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
    public void writeToField() {
        text.setText(targetDiary.getText());
        checkBox.setChecked(targetDiary.isStatus());
        if (Calendar.getInstance().getTimeInMillis() < targetDiary.getDate()) {
            time.setText(new SimpleDateFormat("HH:mm").format(targetDiary.getDate()));
        }else {
            time.setText("--:--");
        }
    }

    @Override
    public void findById(View root) {
        context = root.getContext();
        checkBox = root.findViewById(R.id.completeTarget);
        text = root.findViewById(R.id.textTarget);
        time = root.findViewById(R.id.timeTarget);
        childItem = root.findViewById(R.id.child_item_xml);
    }

    @Override
    public void setAdaptive() {

    }

    @Override
    public void activity() {
        childItem.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(CHANGE).setOnMenuItemClickListener(item -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CHILD_ITEM_TARGET, targetDiary);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_change_target);
                return true;
            });
            menu.add(DELETE).setOnMenuItemClickListener(item -> {
                TargetDataService service = new TargetDataService(context);
                service.delete(targetDiary.getDate());
                return true;
            });
        });
    }
}
