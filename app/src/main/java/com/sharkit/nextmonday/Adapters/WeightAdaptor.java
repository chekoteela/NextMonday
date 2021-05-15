package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.WeightV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WeightAdaptor extends BaseAdapter {

    TextView date, weight, change;
    Context mContext;
    ArrayList<WeightV> list;
    final String TAG = "qwerty";

    public WeightAdaptor(Context mContext, ArrayList<WeightV> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calculator_weight_item, null);
        }
        FindView(convertView);

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        change.setText(list.get(position).getChange());
        weight.setText(list.get(position).getWeight());
        date.setText(dateFormat.format(list.get(position).getDate()));
//        Log.d(TAG, dateFormat.format(list.get(position).getDate())  );

        return convertView;
    }

    private void FindView(View convertView) {
        change = convertView.findViewById(R.id.change);
        weight = convertView.findViewById(R.id.weight);
        date = convertView.findViewById(R.id.date);
    }
}
