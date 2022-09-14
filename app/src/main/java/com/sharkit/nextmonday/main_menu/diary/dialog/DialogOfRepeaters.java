package com.sharkit.nextmonday.main_menu.diary.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DialogOfRepeaters {

    private final Context context;
    private final DiaryTask diaryTask;

    private static final String TAG = DialogOfRepeaters.class.getCanonicalName();

    public void showIfChecked(final Boolean isChecked) {
        if (Boolean.TRUE.equals(isChecked)) {
            this.showDialogOfRepeaters();
        } else {
            this.diaryTask.setRepeats(null);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void showDialogOfRepeaters() {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.diary_list_of_repeat, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this.context)
                .setView(view);

        final WidgetContainer.Dialog.RepeatersWidget dialogWidget = WidgetContainer.newInstance(view).getDialog().getRepeatersWidget();

        dialogWidget.getRadioGroup().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.repeat_everyday_id:
                    dialogWidget.getCheckBoxList().setVisibility(View.GONE);
                    this.diaryTask.setRepeats(Arrays.asList(DayOfRepeat.values()));
                    break;
                case R.id.select_day_id:
                    dialogWidget.getCheckBoxList().setVisibility(View.VISIBLE);
                    this.diaryTask.setRepeats(new ArrayList<>());
                    break;
                default:
                    this.diaryTask.setRepeats(null);
            }
        });

        this.setCheckBoxActivity(dialogWidget.getEveryDayWidget());

        dialog.setPositiveButton(this.context.getString(R.string.button_accept),
                (dialog1, which) -> {
                    if (Boolean.TRUE.equals(dialogWidget.getEveryDay().isChecked())) {
                        this.diaryTask.setRepeats(Arrays.asList(DayOfRepeat.values()));
                    } else if (Boolean.TRUE.equals(this.diaryTask.getRepeats().isEmpty())) {
                        this.diaryTask.setRepeats(null);
                    }

                    Log.i(TAG, String.format("Set days for repeat : %s", Optional.ofNullable(this.diaryTask.getRepeats()).orElse(new ArrayList<>())));

                    dialog1.dismiss();
                });
        dialog.show();
    }

    private void setCheckBoxActivity(final WidgetContainer.Dialog.RepeatersWidget.EveryDayWidget widget) {
        this.setOnChecked(widget.getMonday(), DayOfRepeat.MONDAY);
        this.setOnChecked(widget.getTuesday(), DayOfRepeat.TUESDAY);
        this.setOnChecked(widget.getWednesday(), DayOfRepeat.WEDNESDAY);
        this.setOnChecked(widget.getThursday(), DayOfRepeat.THURSDAY);
        this.setOnChecked(widget.getFriday(), DayOfRepeat.FRIDAY);
        this.setOnChecked(widget.getSaturday(), DayOfRepeat.SATURDAY);
        this.setOnChecked(widget.getSunday(), DayOfRepeat.SUNDAY);
    }

    private void setOnChecked(final CheckBox checkBox, final DayOfRepeat dayOfRepeat) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> this.setDayForRepeat(isChecked, dayOfRepeat));
    }

    private void setDayForRepeat(final boolean isChecked, final DayOfRepeat dayOfRepeat) {
        if (isChecked) {
            this.diaryTask.getRepeats().add(dayOfRepeat);
        } else {
            this.diaryTask.getRepeats().remove(dayOfRepeat);
        }
    }
}
