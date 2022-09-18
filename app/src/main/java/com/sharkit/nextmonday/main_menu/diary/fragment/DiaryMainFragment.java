package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.configuration.utils.notification.Notification.BIG_TEXT;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.CONTENT_TITLE;
import static com.sharkit.nextmonday.configuration.utils.notification.Notification.DIARY_TASK_ID;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_DAY_OF_WEEK;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DayInfoTransformer.toDayName;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DayInfoTransformer.toDayOfWeek;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DayInfoTransformer.toMonthName;
import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTasks;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.configuration.widget.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.DiaryMainListAdapter;
import com.sharkit.nextmonday.main_menu.diary.configuration.AlarmDiary;
import com.sharkit.nextmonday.main_menu.diary.domain.DayInfo;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class DiaryMainFragment extends Fragment {

    private static final String TAG = DiaryMainFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_main, container, false);
        final WidgetContainer.DiaryMainWidget widget = WidgetContainer.newInstance(view).getDiaryMainWidget();
        final List<DayInfo> daysInfo = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(Optional.ofNullable(this.getArguments())
                .map(arg -> arg.getLong(DIARY_DAY_OF_WEEK))
                .orElse(Calendar.getInstance().getTimeInMillis()));

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        for (int i = 0; i < 7; i++) {
            this.fillOutList(daysInfo, calendar);

            Log.i(TAG, String.format("Looking for all tasks by: %s", DateFormat.getDateInstance().format(calendar.getTime())));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        widget.getExpandableListView().setAdapter(new DiaryMainListAdapter(daysInfo, this.getContext()));
        return view;
    }

    private void fillOutList(final List<DayInfo> daysInfo, final Calendar calendar) {
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.getContext());

        final List<DiaryTask> diaryTasks = toDiaryTasks(db.dairyTaskDAO()
                .findAllByDate(DateFormat.getDateInstance().format(calendar.getTime())));
        daysInfo.add(DayInfo.builder()
                .dayOfWeek(toDayName(this.getContext(), calendar.get(Calendar.DAY_OF_WEEK)))
                .month(toMonthName(this.getContext(), calendar.get(Calendar.MONTH)))
                .dayNumber(calendar.get(Calendar.DATE))
                .dataTime(calendar.getTimeInMillis())
                .diaryTasks(diaryTasks)
                .build());

        Optional.of(diaryTasks)
                .filter(f -> calendar.get(Calendar.DATE) == Calendar.getInstance().get(Calendar.DATE))
                .ifPresent(diaryTask -> diaryTask
                        .stream().filter(f -> !f.getRepeated())
                        .forEach(task -> this.repeat(task.getRepeats(), task)));

        diaryTasks.stream()
                .filter(f -> f.getAlarm() && !f.getCompleted() && f.getTimeForRepeat() > calendar.getTimeInMillis())
                .forEach(this::setTime);
    }

    private void repeat(final List<DayOfRepeat> repeats, final DiaryTask diaryTask) {
        Optional.ofNullable(repeats)
                .ifPresent(repeat -> repeat.forEach(dayOfRepeat -> dayOfRepeat.repeat(diaryTask, toDayOfWeek(dayOfRepeat), this.getContext())));
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void setTime(final DiaryTask diaryTask) {

        Log.i(TAG, String.format("Set time: %s for task: %s", diaryTask, diaryTask.getTimeForRepeat()));

        final Intent intent = new Intent(this.getContext(), AlarmDiary.class);
        intent.putExtra(CONTENT_TITLE, diaryTask.getName());
        intent.putExtra(BIG_TEXT, diaryTask.getDescription());
        intent.putExtra(DIARY_TASK_ID, diaryTask.getId());

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), Math.toIntExact(diaryTask.getId()), intent, 0);
        final AlarmManager alarmManager = (AlarmManager) this.requireContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, diaryTask.getTimeForRepeat(), pendingIntent);
    }

}
