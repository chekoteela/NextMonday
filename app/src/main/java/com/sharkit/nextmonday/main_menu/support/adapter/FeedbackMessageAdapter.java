package com.sharkit.nextmonday.main_menu.support.adapter;

import static java.util.Objects.isNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.support.adapter.widget.SupportAdapterWidget;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackMessage;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedbackMessageAdapter extends BaseAdapter {

    private final List<FeedbackMessage> messages;
    private final Context context;

    @Override
    public int getCount() {
        return this.messages.size();
    }

    @Override
    public FeedbackMessage getItem(final int position) {
        return this.messages.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (isNull(convertView))
            convertView = LayoutInflater.from(this.context).inflate(R.layout.support_feedback_message_item, null);

        final SupportAdapterWidget.FeedbackMessageWidget widget = SupportAdapterWidget.newInstance(convertView).getFeedbackMessageWidget();

        widget.getMessageText().setText(this.getItem(position).getMessage());

        return convertView;
    }
}
