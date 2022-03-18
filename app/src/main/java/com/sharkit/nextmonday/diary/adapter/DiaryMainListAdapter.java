package com.sharkit.nextmonday.diary.adapter;

import static com.sharkit.nextmonday.configuration.constant.ButtonText.CHANGE;
import static com.sharkit.nextmonday.configuration.constant.ButtonText.DELETE;
import static com.sharkit.nextmonday.diary.constant.DiaryConstant.DATE_FOR_CREATE;
import static com.sharkit.nextmonday.diary.constant.DiaryConstant.OBJECT_FOR_UPDATE;
import static com.sharkit.nextmonday.diary.transformer.DateTransformer.toDayName;
import static com.sharkit.nextmonday.diary.transformer.DateTransformer.toMonthName;

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
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.diary.entity.TaskOfDay;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

@SuppressLint("InflateParams")
public class DiaryMainListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<TaskOfDay> taskOfDays;

    private static final String EMPTY_TIME_FORMAT = "--:--";

    public DiaryMainListAdapter(Context context, List<TaskOfDay> taskOfDays) {
        this.context = context;
        this.taskOfDays = taskOfDays;
    }

    @Override
    public int getGroupCount() {
        return taskOfDays.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return taskOfDays.get(groupPosition).getDiaryTasks().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return taskOfDays.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.diary_main_parent_list, null);
        }
        Widget widget = Widget.findByView(convertView);
        onClick(widget, groupPosition);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(taskOfDays.get(groupPosition).getTimeInMillis());

        widget.getTextView().getDayNumber().setText(String.valueOf(calendar.get(Calendar.DATE)));
        widget.getTextView().getMonth().setText(toMonthName(context, calendar.get(Calendar.MONTH)));
        widget.getTextView().getDayName().setText(toDayName(context, calendar.get(Calendar.DAY_OF_WEEK)));
        widget.getTextView().getCompleteTask().setText(String.valueOf(taskOfDays.get(groupPosition)
                .getDiaryTasks()
                .stream()
                .filter(DiaryTask::isStatus)
                .count()));
        widget.getTextView().getAllTasks().setText(String.valueOf(taskOfDays.get(groupPosition).getDiaryTasks().size()));

        if (taskOfDays.get(groupPosition).getDiaryTasks().size() != 0) {
            int i = (int) (taskOfDays.get(groupPosition).getDiaryTasks().stream().filter(DiaryTask::isStatus).count() * 100 / taskOfDays.get(groupPosition).getDiaryTasks().size());
            widget.getProgressBar().getCompleteTaskProgress()
                    .setProgress(i);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.diary_main_child_item, null);
        }
        Widget widget = Widget.findByView(convertView);
        onClick(widget, groupPosition, childPosition);

        Calendar calendar = Calendar.getInstance();

        if (taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getHour() == -1 ||
                taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getMinute() == -1) {
            widget.getTextView().getTimeTask().setText(EMPTY_TIME_FORMAT);
        } else {
            calendar.set(Calendar.HOUR, taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getHour());
            calendar.set(Calendar.MINUTE, taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getMinute());
            widget.getTextView().getTimeTask().setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTimeInMillis()));
        }
        widget.getTextView().getTextTask().setText(taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getNameOfTask());
        widget.getCheckBox().isCompleteTask().setChecked(taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).isStatus());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void onClick(Widget widget, int groupPosition, int childPosition) {
        widget.getCheckBox().isCompleteTask().setOnCheckedChangeListener((buttonView, isChecked) -> {
            DiaryTaskRepo.getInstance(context).updateStatus(isChecked, taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getId());
            taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).setStatus(isChecked);
            notifyDataSetChanged();
        });

        widget.getRelativeLayout().getChild().setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(DELETE(context)).setOnMenuItemClickListener(item -> {
                DiaryTaskRepo.getInstance(context).delete(taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition).getId());
                taskOfDays.get(groupPosition).getDiaryTasks().remove(childPosition);
                notifyDataSetChanged();
                return true;
            });
            menu.add(CHANGE(context)).setOnMenuItemClickListener(item -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(OBJECT_FOR_UPDATE, taskOfDays.get(groupPosition).getDiaryTasks().get(childPosition));
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment)
                        .navigate(R.id.navigation_diary_update_task, bundle);
                return true;
            });
        });
    }

    private void onClick(Widget widget, int groupPosition) {
        widget.getImageView().getPlus().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong(DATE_FOR_CREATE, taskOfDays.get(groupPosition).getTimeInMillis());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment)
                    .navigate(R.id.navigation_diary_create_task, bundle);
        });
    }
}

