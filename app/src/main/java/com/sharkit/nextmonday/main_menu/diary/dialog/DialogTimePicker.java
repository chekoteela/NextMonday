package com.sharkit.nextmonday.main_menu.diary.dialog;

import android.app.TimePickerDialog;
import android.content.Context;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DialogTimePicker {

    private final Context context;
    private final DiaryTask diaryTask;
    private final SwitchMaterial switchMaterial;

    public void showIfChecked(Boolean isChecked){
        if (Boolean.TRUE.equals(isChecked)) {
            showTimePicker();
        } else {
            diaryTask.setHour(null);
            diaryTask.setMinute(null);
        }
    }

    private void showTimePicker() {
        final TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.time_picker_style, (view, hourOfDay, minuteOfHour) -> {
            this.diaryTask.setHour(hourOfDay);
            this.diaryTask.setMinute(minuteOfHour);
        }, Optional.ofNullable(this.diaryTask.getHour()).orElse(0),
                Optional.ofNullable(this.diaryTask.getMinute()).orElse(0),
                true);
        timePickerDialog.setOnCancelListener(dialog -> switchMaterial.setChecked(false));
        timePickerDialog.show();
    }
}
