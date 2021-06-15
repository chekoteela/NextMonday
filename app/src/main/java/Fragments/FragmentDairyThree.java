package Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;
import com.sharkit.nextmonday.Adapters.MyCustomFindAdapter;
import com.sharkit.nextmonday.Users.Users;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentDairyThree extends Fragment {

     DatabaseHelper databaseHelper;
     SQLiteDatabase db;
     ArrayList<String> arrayText, arrayTime, arrayData, arrayAllTime;
     ArrayList<Boolean> arrayComplete;
     MyCustomFindAdapter adapter;
     ArrayList<String> groups;
     Cursor query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_three, container, false);
        ListView listFind = root.findViewById(R.id.listFind);
        EditText find = root.findViewById(R.id.find);
        WriteAllTarget(listFind);

        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(find.getText().toString().equals("")){
                WriteAllTarget(listFind);
            }else{
                WriteSortTarget(listFind,root);
            }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return root;
    }

    public void WriteAllTarget(ListView list) {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        groups = new ArrayList<String>();
        arrayText = new ArrayList<>();
        arrayData = new ArrayList<>();
        arrayAllTime = new ArrayList<>();
        arrayComplete = new ArrayList<>();
        arrayTime = new ArrayList<>();

         query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " + databaseHelper.COLUMN_ID + " = '" + Users.getUID() + "'",null);
        while (query.moveToNext()) {
            groups.add("item");
            arrayAllTime.add(query.getString(16));
            arrayText.add(query.getString(1));
            if(query.getInt(12) == -1 && query.getInt(11) == -1){
                arrayTime.add("--:--");
            }else {
                String hour = String.valueOf(query.getInt(12));
                String minute = String.valueOf(query.getInt(11));
                if (query.getInt(12) < 10){
                    hour = "0" + hour;
                }
                if (query.getInt(11) < 10){
                    minute = "0" + minute;
                }

                    arrayTime.add(hour + ":" + minute);

            }
            arrayData.add(query.getInt(14) + "." + query.getInt(13) + "." + query.getInt(15));
            if (query.getInt(2) == 0) {
                arrayComplete.add(false);
            } else {
                arrayComplete.add(true);
            }
        }
        adapter = new MyCustomFindAdapter(groups, getApplicationContext(), arrayTime, arrayComplete, arrayAllTime, arrayData, arrayText);
        list.setAdapter(adapter);


    }

    public void WriteSortTarget(ListView listView, View v){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        groups = new ArrayList<String>();
        arrayText = new ArrayList<>();
        arrayData = new ArrayList<>();
        arrayAllTime = new ArrayList<>();
        arrayComplete = new ArrayList<>();
        arrayTime = new ArrayList<>();

        EditText find = v.findViewById(R.id.find);

        query = db.rawQuery("SELECT * FROM " + databaseHelper.TABLE + " WHERE " +databaseHelper.COLUMN_ID + " = '" + Users.getUID()
                + "' AND " + databaseHelper.COLUMN_TEXT_TARGET +" LIKE '" + find.getText().toString() + "%'", null);

        while (query.moveToNext()) {
            groups.add("item");
            arrayAllTime.add(query.getInt(14) + "-" + query.getInt(13) + "-" + query.getInt(15) + " " + query.getInt(12) + ":" + query.getInt(11));
            arrayText.add(query.getString(1));
            if(query.getInt(12) == -1 && query.getInt(11) == -1){
                arrayTime.add("--:--");
            }else {
                String hour = String.valueOf(query.getInt(12));
                String minute = String.valueOf(query.getInt(11));
                if (query.getInt(12) < 10){
                    hour = "0" + hour;
                }
                if (query.getInt(11) < 10){
                    minute = "0" + minute;
                }

                arrayTime.add(hour + ":" + minute);

            }
            arrayData.add(query.getInt(14) + "." + query.getInt(13) + "." + query.getInt(15));
            if (query.getInt(2) == 0) {
                arrayComplete.add(false);
            } else {
                arrayComplete.add(true);
            }
        }
        adapter = new MyCustomFindAdapter(groups, getApplicationContext(), arrayTime, arrayComplete, arrayAllTime, arrayData, arrayText);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        db.close();
        query.close();
        super.onStop();
    }
}