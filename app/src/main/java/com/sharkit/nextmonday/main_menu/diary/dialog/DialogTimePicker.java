package com.sharkit.nextmonday.main_menu.diary.dialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.fragment.DiaryMainFragment;

import java.text.DateFormat;
import java.util.Calendar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DialogTimePicker {

    private final Context context;
    private final DiaryTask diaryTask;
    private final SwitchMaterial switchMaterial;
    private final Calendar calendar;

    private static final String TAG = DialogTimePicker.class.getCanonicalName();

    public void showIfChecked(final Boolean isChecked){
        this.diaryTask.setAlarm(isChecked);
        if (Boolean.TRUE.equals(isChecked)) {
            showTimePicker();
        } else {
            this.diaryTask.setTimeForRepeat(Calendar.getInstance().getTimeInMillis());
        }
    }

    private void showTimePicker() {
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this.context, R.style.time_picker_style, (view, hourOfDay, minuteOfHour) -> {

            this.calendar.set(this.calendar.get(Calendar.YEAR),
                    this.calendar.get(Calendar.MONTH),
                    this.calendar.get(Calendar.DATE),
                    hourOfDay, minuteOfHour, 0);

            Log.i(TAG, String.format("Set time: %s for task", DateFormat.getTimeInstance().format(this.calendar.getTime())));

            this.diaryTask.setTimeForRepeat(this.calendar.getTimeInMillis());
        }, 0, 0, true);
        timePickerDialog.setOnCancelListener(dialog -> this.switchMaterial.setChecked(false));
        timePickerDialog.show();
    }
}
