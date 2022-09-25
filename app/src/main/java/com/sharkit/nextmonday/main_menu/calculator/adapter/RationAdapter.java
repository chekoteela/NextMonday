package com.sharkit.nextmonday.main_menu.calculator.adapter;

import static java.util.Objects.isNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressLint("InflateParams")
@Getter
@RequiredArgsConstructor
public class RationAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<Ration> rations;

    @Override
    public int getGroupCount() {
        return this.rations.size();
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return 0;
    }

    @Override
    public Ration getGroup(final int groupPosition) {
        return this.rations.get(groupPosition);
    }

    @Override
    public Ration getChild(final int groupPosition, final int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(final int groupPosition, final int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        if (isNull(convertView))
            convertView = LayoutInflater.from(this.context).inflate(R.layout.calculator_food_parent_list, null);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
        if (isNull(convertView))
            convertView = LayoutInflater.from(this.context).inflate(R.layout.calculator_food_item_list, null);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return false;
    }
}
