package com.sharkit.nextmonday.ui.Calculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.sharkit.nextmonday.Adapters.WeightAdaptor;
import com.sharkit.nextmonday.MySQL.MyWeight;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.PFC_today;
import com.sharkit.nextmonday.variables.WeightV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

    TextView weight,current_weight;
    Button add_weight;


//    LineGraphSeries<> series;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.calculator_weight, container, false);


        FindView(root);

        Adapter();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");


        graphView.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.white));
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.white));
        graphView.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.white));
        graphView.setBackgroundColor(getResources().getColor(R.color.calculator_color));


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);


        graphView.addSeries(series);
        series.resetData(GetDataPoint());
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(45);

//        graphView.getGridLabelRenderer().setNumHorizontalLabels(10); // вказує максимальну кількість точок



        try {
            graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                @Override
                public String formatLabel(double value, boolean isValueX) {

                    if (isValueX) {
                        return dateFormat.format(value);
                    }else{
                        return super.formatLabel(value, isValueX);
                    }
                }

            });
            graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
            graphView.getViewport().setXAxisBoundsManual(true);
            query.moveToFirst();
            graphView.getViewport().setMinX(query.getLong(1));  // минимальная  первая точка
            query.moveToLast();
            graphView.getViewport().setMaxX(query.getLong(1));  // максимальная последняя точка

        }catch (CursorIndexOutOfBoundsException e){
            graphView.getGridLabelRenderer().setNumHorizontalLabels(0);
        }

        graphView.getViewport().setScrollable(true);  // scrolling x
        graphView.getViewport().setScalable(true); // zooming x

        series.setDrawDataPoints(true); // намалювати пікові точки
        series.setDataPointsRadius(16);//радіус точок на лінії
        series.setThickness(8);//товщина основної лінії

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getContext(),"На " + dateFormat.format(dataPoint.getX()) + "\nВы весите : " + dataPoint.getY(), Toast.LENGTH_LONG).show();
            }
        });

        add_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.calculator_weigth_button_dialog, null);
                EditText weight = existProduct.findViewById(R.id.weight);
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WriteDataWeight(weight.getText().toString());
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });

        return root;
    }


    private DataPoint[] GetDataPoint(){

        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);
        Cursor cursor = sdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID +
                " = '" + mAuth.getCurrentUser().getUid() + "'", null);

        DataPoint[] dp = new DataPoint[cursor.getCount()];
        try {

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();
            dp[i] =
                    new DataPoint(cursor.getLong(1),
                            cursor.getFloat(2));

        }
        cursor.moveToLast();
        try {
            weight.setText(String.valueOf(cursor.getFloat(2)));
            current_weight.setText(PFC_today.getCurrent_weight());
        }catch (NullPointerException e){
        }

        }catch (CursorIndexOutOfBoundsException e){
            try {

                weight.setText(PFC_today.getWeight());
                current_weight.setText(PFC_today.getCurrent_weight());
            }catch (NullPointerException exception){
                weight.setText("");
                current_weight.setText("");
            }
        }

        return dp;
    }

    private void Adapter() {

        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);
        query = sdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);
        query.moveToPosition(query.getCount() +1);

        list = new ArrayList<>();

        while (query.moveToPrevious()) {
        WeightV weightV = new WeightV();
        weightV.setChange(query.getString(3));
        weightV.setDate(query.getLong(1));
        weightV.setWeight(query.getString(2));
        list.add(weightV);
        }

        adaptor = new WeightAdaptor(getApplicationContext(), list);
        listView.setAdapter(adaptor);
    }



    private void WriteDataWeight(String s) {

        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        query = sdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);

        query.moveToLast();

        WeightV weight = new WeightV();
        weight.setDate(calendar.getTimeInMillis());
        weight.setWeight(s);

        try {
            weight.setChange(String.format( Locale.ROOT,"%.1f",(Float.parseFloat(s) - Float.parseFloat(query.getString(2)))));
        }catch (CursorIndexOutOfBoundsException e){
            weight.setChange(String.valueOf(0));
        }

        try {
            sdb.execSQL("INSERT INTO " + myWeight.TABLE + " VALUES ('" + mAuth.getCurrentUser().getUid() + "','" +
                    calendar.getTimeInMillis() + "','" +
                    s + "','" + weight.getChange() + "','" +
                    dateFormat.format(calendar.getTimeInMillis()) +
                    "');");

            CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
            colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weight);

        }catch (SQLiteConstraintException e){

            try {
                query.moveToPosition(query.getCount() -2);
                weight.setChange(String.valueOf(Float.parseFloat(s) - Float.parseFloat(query.getString(2))));

            }catch (CursorIndexOutOfBoundsException exception){
            }

            try {
                weight.setChange(String.format( Locale.ROOT,"%.1f",(Float.parseFloat(s) - Float.parseFloat(query.getString(2)))));
            }catch (CursorIndexOutOfBoundsException ex){
                weight.setChange(String.valueOf(0));
            }

            sdb.execSQL("UPDATE " + myWeight.TABLE + " SET " +
                            myWeight.COLUMN_WEIGHT + " = '" + s + "'," +
                            myWeight.COLUMN_CHANGE + " = '" + weight.getChange()
                            + "' WHERE " +
                            myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "' AND " +
                            myWeight.COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "'" );
            CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
            colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weight);

        }


    }

    private void FindView(View root) {
        add_weight = root.findViewById(R.id.add_weight);
        weight = root.findViewById(R.id.my_weight);
        current_weight = root.findViewById(R.id.current_weight);
        graphView = root.findViewById(R.id.graph);
        listView = root.findViewById(R.id.list_item);
    }
}