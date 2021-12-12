package com.sharkit.nextmonday.adapter.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.calculator.WeightDTO;

import java.util.ArrayList;

public class WeightAdapter extends BaseAdapter {
    private final ArrayList<WeightDTO> group;
    private final Context context;
    private TextView date, difference, weight;

    public WeightAdapter(ArrayList<WeightDTO> group, Context context) {
        this.group = group;
        this.context = context;
    }

    @Override
    public int getCount() {
        return group.size();
    }

    @Override
    public Object getItem(int position) {
        return group.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calculator_weight_item, null);
        }
        findView(convertView);
        if (group.get(position).getDifference() < 0) {
            difference.setTextColor(context.getColor(R.color.red));
        } else if (group.get(position).getDifference() > 0) {
            difference.setTextColor(context.getColor(R.color.teal_700));
        }
        difference.setText(String.valueOf(group.get(position).getDifference()));
        date.setText(String.valueOf(group.get(position).getDate()));
        weight.setText(String.valueOf(group.get(position).getWeight()));

        return convertView;
    }

    private void findView(View root) {
        difference = root.findViewById(R.id.difference_xml);
        weight = root.findViewById(R.id.weight_xml);
        date = root.findViewById(R.id.date_xml);
    }
}
