package com.sharkit.nextmonday.service.diary.main_diary_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ALERT_DELETE_TEXT;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.DELETE_EVERYTHING_SIMILAR;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.DELETE_ONE;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_HOUR_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.WITHOUT_TIME;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.CHILD_ITEM_TARGET;
import static com.sharkit.nextmonday.configuration.constant.MenuItemName.CHANGE;
import static com.sharkit.nextmonday.configuration.constant.MenuItemName.DELETE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.diary.DiaryFirestore;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;

public class ChildService implements LayoutService {
    private Context context;
    private CheckBox checkBox;
    private TextView text, time, textDelete;
    private RelativeLayout childItem;
    private final ChildItemTargetDTO targetDiary;
    private TargetDataService service;
    private DiaryFirestore diaryFirestore;

    public ChildService(ChildItemTargetDTO targetDiary) {
        this.targetDiary = targetDiary;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService writeToField() {
        text.setText(targetDiary.getText());
        checkBox.setChecked(targetDiary.isStatus());
        if (targetDiary.isAlarm()) {
            time.setText(new SimpleDateFormat(SHOW_HOUR_FORMAT).format(targetDiary.getDate()));
        } else {
            time.setText(WITHOUT_TIME);
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
        service = new TargetDataService(context);
        diaryFirestore = new DiaryFirestore();
        childItem.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(CHANGE).setOnMenuItemClickListener(item -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CHILD_ITEM_TARGET, targetDiary);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_change_target, bundle);
                return true;
            });
            menu.add(DELETE).setOnMenuItemClickListener(item -> {

                if (service.getRepeatForAlarmDTO(targetDiary.getDate()).toArray().contains(true)) {
                    createDeleteAlertDialog();
                } else {
                    service.delete(targetDiary.getDate());
                    diaryFirestore.deleteById(targetDiary.getDate());
                }
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
                return true;
            });
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            service.setCheckedTarget(targetDiary.getDate(), isChecked);
            diaryFirestore.updateStatus(isChecked, targetDiary.getDate());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
        });
        return this;
    }

    @SuppressLint("InflateParams")
    private void createDeleteAlertDialog() {
        NavController navController = Navigation.findNavController((Activity) context,R.id.nav_host_fragment);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_simple_text, null);
        findViewForAlertDialog(view);
        textDelete.setText(ALERT_DELETE_TEXT);
        dialog.setPositiveButton(DELETE_EVERYTHING_SIMILAR, (dialog1, which) -> {
            service.deleteSimilar(targetDiary.isAlarm(), targetDiary.getText(), targetDiary.getDescription());
            diaryFirestore.deleteSimilar(targetDiary.isAlarm(), targetDiary.getText(), targetDiary.getDescription(),navController);
        });
                dialog.setNegativeButton(DELETE_ONE, (dialog12, which) -> {
                    service.delete(targetDiary.getDate());
                    diaryFirestore.deleteById(targetDiary.getDate());
                    navController.navigate(R.id.nav_diary);
                });
                dialog.setView(view);
        dialog.show();
    }

    private void findViewForAlertDialog(View view) {
        textDelete = view.findViewById(R.id.text_xml);
    }
}