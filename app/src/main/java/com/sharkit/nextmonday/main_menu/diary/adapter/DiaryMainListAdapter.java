package com.sharkit.nextmonday.main_menu.diary.adapter;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_DAY_OF_WEEK;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_TASK_FOR_CHANGE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogChangeTask;
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
        return this.daysInfo.size();
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return this.daysInfo.get(groupPosition).getDiaryTasks().size();
    }

    @Override
    public Object getGroup(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(final int groupPosition, final int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(final int groupPosition, final int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.diary_main_parent_list, null);
        }
        final WidgetContainer.DiaryMainWidget.DiaryMainParentWidget widget = WidgetContainer.newInstance(convertView).getDiaryMainWidget().getParentWidget();
        final Long allCompleted = this.daysInfo.get(groupPosition)
                .getDiaryTasks()
                .stream()
                .filter(DiaryTask::getCompleted)
                .count();

        widget.getDayName().setText(this.daysInfo.get(groupPosition).getDayOfWeek());
        widget.getMonthName().setText(this.daysInfo.get(groupPosition).getMonth());
        widget.getDayNumber().setText(String.valueOf(this.daysInfo.get(groupPosition).getDayNumber()));

        widget.getAllTask().setText(String.valueOf(this.daysInfo.get(groupPosition).getDiaryTasks().size()));
        widget.getCompletedTask().setText(String.valueOf(allCompleted));

        Optional.of(allCompleted)
                .filter(aLong -> { widget.getTaskProgress().setProgress(0);
                    return aLong != 0; })
                .ifPresent(aLong -> widget.getTaskProgress().setProgress((int) (this.daysInfo.get(groupPosition).getDiaryTasks().size() / aLong * 100)));

        widget.getCreate().setOnClickListener(v -> this.moveToCreateTask(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.diary_main_child_item, null);
        }
        final WidgetContainer.DiaryMainWidget.DiaryMainParentWidget.DiaryMainChildWidget widget = WidgetContainer.newInstance(convertView).getDiaryMainWidget().getParentWidget().getChildWidget();
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getTimeForRepeat());

        widget.getGetByTask().setChecked(this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getCompleted());
        widget.getTextTask().setText(this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getName());
        widget.getTimeTask().setText(DateFormat.getTimeInstance().format(calendar.getTime()));

        widget.getGetByTask().setOnCheckedChangeListener((buttonView, isChecked) -> {
            NextMondayDatabase.getInstance(this.context).dairyTaskDAO().updateStatus(this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).getId(), isChecked);
            this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition).setCompleted(isChecked);
            this.notifyDataSetChanged();
        });

        widget.getChildItem().setOnCreateContextMenuListener((menu, v, menuInfo) -> this.showContextMenu(menu, groupPosition, childPosition));
        return convertView;
    }

    private void moveToCreateTask(final int groupPosition) {
        final Bundle time = new Bundle();
        time.putLong(DIARY_DAY_OF_WEEK, this.daysInfo.get(groupPosition).getDataTime());
        Navigation.findNavController((Activity) this.context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_create_task, time);
    }

    private void showContextMenu(final Menu menu, final int groupPosition, final int childPosition){
        menu.add(this.context.getString(R.string.button_change)).setOnMenuItemClickListener(item -> {
            final Bundle bundle = new Bundle();

            bundle.putSerializable(DIARY_TASK_FOR_CHANGE, this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition));
            Navigation.findNavController((Activity) this.context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_update_task, bundle);
            return true;
        });
        menu.add(this.context.getString(R.string.button_delete)).setOnMenuItemClickListener(item -> {
            new DialogChangeTask().showDialogDelete(this.context, this.daysInfo.get(groupPosition).getDiaryTasks().get(childPosition));
            this.daysInfo.get(groupPosition).getDiaryTasks().remove(childPosition);
            this.notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return false;
    }
}
