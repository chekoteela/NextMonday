package com.sharkit.nextmonday.main_menu.diary.adapter;

import static com.sharkit.nextmonday.main_menu.NavigationMenu.getContext;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_CALENDAR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.domain.DayInfo;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressLint("InflateParams")
public class DiaryMainListAdapter extends BaseExpandableListAdapter {

    private final List<DayInfo> daysInfo;
    private final Context context;

    @Override
    public int getGroupCount() {
        return daysInfo.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return daysInfo.get(groupPosition).getDiaryTasks().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.diary_main_parent_list, null);
        }
        final WidgetContainer.DiaryMainWidget.DiaryMainParentWidget widget = WidgetContainer.newInstance(convertView).getDiaryMainWidget().getParentWidget();
        final Long allCompleted = daysInfo.get(groupPosition)
                .getDiaryTasks()
                .stream()
                .filter(DiaryTask::getCompleted)
                .count();

        widget.getDayName().setText(daysInfo.get(groupPosition).getDayOfWeek());
        widget.getMonthName().setText(daysInfo.get(groupPosition).getMonth());
        widget.getDayNumber().setText(String.valueOf(daysInfo.get(groupPosition).getDayNumber()));

        widget.getAllTask().setText(String.valueOf(daysInfo.get(groupPosition).getDiaryTasks().size()));
        widget.getCompletedTask().setText(String.valueOf(allCompleted));

        Optional.of(allCompleted)
                .filter(aLong -> { widget.getTaskProgress().setProgress(0);
                    return aLong != 0;
                })
                .ifPresent(aLong -> widget.getTaskProgress().setProgress((int) (daysInfo.get(groupPosition).getDiaryTasks().size() / aLong * 100)));

        widget.getCreate().setOnClickListener(v -> moveToCreateTask(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.diary_main_child_item, null);
        }
        final WidgetContainer.DiaryMainWidget.DiaryMainParentWidget.DiaryMainChildWidget widget = WidgetContainer.newInstance(convertView).getDiaryMainWidget().getParentWidget().getChildWidget();
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getTimeForRepeat());

        widget.getGetByTask().setChecked(daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getCompleted());
        widget.getTextTask().setText(daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getName());
        widget.getTimeTask().setText(DateFormat.getTimeInstance().format(calendar.getTime()));

        widget.getGetByTask().setOnCheckedChangeListener((buttonView, isChecked) -> {
            NextMondayDatabase.getInstance().dairyTaskDAO().updateStatus(daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getId(), isChecked);
            daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).setCompleted(isChecked);
            notifyDataSetChanged();
        });

        return convertView;
    }

    private void moveToCreateTask(int groupPosition) {
        Bundle time = new Bundle();
        time.putLong(DIARY_CALENDAR, daysInfo.get(groupPosition).getDataTime());
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_create_task, time);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
