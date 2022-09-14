package com.sharkit.nextmonday.main_menu.support.adapter;

import static java.util.Objects.isNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.navigation.SupportNavigation;
import com.sharkit.nextmonday.main_menu.support.adapter.widget.SupportAdapterWidget;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackEntity;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MainFeedbackAdapter extends BaseAdapter {

    private final List<FeedbackEntity> entities;
    private final Context context;

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public FeedbackEntity getItem(final int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (isNull(convertView))
            convertView = LayoutInflater.from(context).inflate(R.layout.support_feedback_item, null);

        final SupportAdapterWidget.MainFeedbackAdapterWidget widget = SupportAdapterWidget.newInstance(convertView).getMainFeedbackAdapterWidget();
        final SupportNavigation navigation = SupportNavigation.getInstance(context);

        widget.getText().setText(getItem(position).getFeedbackType().name());
        widget.getParentItem().setOnClickListener(v -> navigation.moveToFeedbackMessenger(getItem(position).getId(), getItem(position).getMessages()));
        return convertView;
    }

}
