package com.sharkit.nextmonday.main_menu.diary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sharkit.nextmonday.main_menu.diary.domain.Notate;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotateAdaptor extends BaseAdapter {

    private final List<Notate> notates;
    private final Context context;

    @Override
    public int getCount() {
        return notates.size();
    }

    @Override
    public Object getItem(int position) {
        return notates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = notates.get(position).getTemplateType().getView(context);

        notates.get(position).getTemplateType().setAction(view, notates.get(position));

        return view;
    }
}


