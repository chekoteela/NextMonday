package com.sharkit.nextmonday.main_menu.diary.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DialogOfRepeaters {

    private final Context context;
    private final DiaryTask diaryTask;

    public void showIfChecked(Boolean isChecked) {
        if (Boolean.TRUE.equals(isChecked)) {
            showDialogOfRepeaters();
        } else {
            diaryTask.setRepeats(null);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void showDialogOfRepeaters() {
        final View view = LayoutInflater.from(context).inflate(R.layout.diary_list_of_repeat, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context).setView(view);

        final WidgetContainer.Dialog.RepeatersWidget dialogWidget = WidgetContainer.newInstance(view).getDialog().getRepeatersWidget();

        dialogWidget.getRadioGroup().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.repeat_everyday_id:
                    dialogWidget.getCheckBoxList().setVisibility(View.GONE);
                    diaryTask.setRepeats(Arrays.asList(DayOfRepeat.values()));
                    break;
                case R.id.select_day_id:
                    dialogWidget.getCheckBoxList().setVisibility(View.VISIBLE);
                    diaryTask.setRepeats(new ArrayList<>());
                    break;
                default:
                    diaryTask.setRepeats(null);
            }
        });

        this.setCheckBoxActivity(dialogWidget.getEveryDayWidget());

        dialog.setPositiveButton(context.getString(R.string.button_accept),
                (dialog1, which) -> {
                    if (Boolean.TRUE.equals(dialogWidget.getEveryDay().isChecked())) {
                        diaryTask.setRepeats(Arrays.asList(DayOfRepeat.values()));
                    } else if (Boolean.TRUE.equals(diaryTask.getRepeats().isEmpty())) {
                        diaryTask.setRepeats(null);
                    }
                    dialog1.dismiss();
                });
        dialog.show();
    }

    private void setCheckBoxActivity(WidgetContainer.Dialog.RepeatersWidget.EveryDayWidget widget) {
        setOnChecked(widget.getMonday(), DayOfRepeat.MONDAY);
        setOnChecked(widget.getTuesday(), DayOfRepeat.TUESDAY);
        setOnChecked(widget.getWednesday(), DayOfRepeat.WEDNESDAY);
        setOnChecked(widget.getThursday(), DayOfRepeat.THURSDAY);
        setOnChecked(widget.getFriday(), DayOfRepeat.FRIDAY);
        setOnChecked(widget.getSaturday(), DayOfRepeat.SATURDAY);
        setOnChecked(widget.getSunday(), DayOfRepeat.SUNDAY);
    }

    private void setOnChecked(CheckBox checkBox, DayOfRepeat dayOfRepeat) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> setDayForRepeat(isChecked, dayOfRepeat));
    }

    private void setDayForRepeat(boolean isChecked, DayOfRepeat dayOfRepeat) {
        if (isChecked) {
            diaryTask.getRepeats().add(dayOfRepeat);
        } else {
            diaryTask.getRepeats().remove(dayOfRepeat);
        }
    }
}
