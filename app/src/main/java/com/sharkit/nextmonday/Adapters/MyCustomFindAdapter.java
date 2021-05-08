package com.sharkit.nextmonday.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;

import java.util.ArrayList;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MyCustomFindAdapter extends BaseAdapter {
          final String TAG = "qwerty";

          DatabaseHelper databaseHelper;
          SQLiteDatabase db;
          ArrayList<String> mGroups, arrayTime, arrayAllTime, arrayData, arrayText ;
           ArrayList<Boolean> arrayComplete;
          Context mContext;



    public MyCustomFindAdapter(ArrayList<String> mGroups, Context mContext,ArrayList<String> arrayTime,
                               ArrayList<Boolean> arrayComplete,ArrayList<String> arrayAllTime,
                               ArrayList<String> arrayData,ArrayList<String> arrayText) {
        this.mGroups = mGroups;
        this.mContext = mContext;
        this.arrayTime = arrayTime;
        this.arrayComplete = arrayComplete;
        this.arrayData = arrayData;
        this.arrayAllTime = arrayAllTime;
        this.arrayText = arrayText;

    }

    @Override
    public int getCount() {
        return mGroups.size();
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
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_custom_find, null);
        }
        CheckBox completeTarget = convertView.findViewById(R.id.checkFind);
        TextView textTarget = convertView.findViewById(R.id.textT);
        TextView timeTarget = convertView.findViewById(R.id.timeT);
        TextView dataTarget = convertView.findViewById(R.id.dateT);

        for(int i = 0; i < mGroups.size(); i++){
            completeTarget.setChecked(arrayComplete.get(position));
            textTarget.setText(arrayText.get(position));
            timeTarget.setText(arrayTime.get(position));
            dataTarget.setText(arrayData.get(position));
        }

        completeTarget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChangeTarget(arrayAllTime.get(position),isChecked);
            }
        });

        return convertView;
    }

    public void ChangeTarget(String time, boolean isChecked) {
        db = databaseHelper.getReadableDatabase();
        if (isChecked) {
            db.execSQL("UPDATE " + databaseHelper.TABLE + " SET " + databaseHelper.COLUMN_COMPLETE + "= 1  WHERE " + databaseHelper.COLUMN_TIME + " = '" + time + "'");
        } else {
            db.execSQL("UPDATE " + databaseHelper.TABLE + " SET " + databaseHelper.COLUMN_COMPLETE + "= 0  WHERE " + databaseHelper.COLUMN_TIME + " = '" + time + "'");
        }
    }

}
