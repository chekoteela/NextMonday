package com.sharkit.nextmonday.ui.Calculator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sharkit.nextmonday.Adapters.WeightAdaptor;
import com.sharkit.nextmonday.MySQL.MyWeight;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.WeightV;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Weight extends Fragment {
    final String TAG = "qwerty";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("DB Product");

    SQLiteDatabase sdb;
    MyWeight myWeight;
    GraphView graphView;
    Cursor query;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<WeightV> list;
    ListView listView;
    WeightAdaptor adaptor;


//    LineGraphSeries<> series;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.calculator_weight, container, false);

        FindView(root);
        Adapter();


        graphView.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.white));
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.white));
        graphView.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.white));
        





        return root;
    }

    private void Adapter() {
        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);
        query = sdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);

        list = new ArrayList<>();

        while (query.moveToNext()) {
        WeightV weightV = new WeightV();
        weightV.setChange(query.getString(3));
        weightV.setDate(query.getString(1));
        weightV.setWeight(query.getString(2));
        list.add(weightV);
        }

        adaptor = new WeightAdaptor(getApplicationContext(), list);
        listView.setAdapter(adaptor);
    }

    private void FindView(View root) {
        graphView = root.findViewById(R.id.graph);
        listView = root.findViewById(R.id.list_item);
    }
}