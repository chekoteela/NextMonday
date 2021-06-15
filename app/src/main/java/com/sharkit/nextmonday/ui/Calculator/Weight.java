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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import com.sharkit.nextmonday.variables.SettingsCalculator;
import com.sharkit.nextmonday.variables.WeightV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.Gravity.CENTER;
import static android.view.TouchDelegate.ABOVE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.isDebugEnabled;


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
    LinearLayout desired_weight, layout_left_xlm;
    TextView weight,current_weight,text_weight_xml,text_current_weight_xml;
    Button add_weight;
    BottomNavigationView bar;
    FirebaseDatabase fs = FirebaseDatabase.getInstance();
    DatabaseReference users = fs.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/MyTarget");
    LinearLayout linear_weight;


//    LineGraphSeries<> series;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.calculator_weight, container, false);
        FindView(root);

        MenuItem item = bar.getMenu().findItem(R.id.weight);
        item.setIcon(R.drawable.my_weight);
        Adapter();
        Adaptive();



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



        layout_left_xlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                           AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.calculator_weigth_button_dialog, null);
                EditText weight = existProduct.findViewById(R.id.weight);
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(weight.getText())){
                            Toast.makeText(getContext(), "Введите ваш вес в кг", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Float.parseFloat(weight.getText().toString()) > 1000 || Float.parseFloat(weight.getText().toString()) < 10){
                            Toast.makeText(getContext(), "Введите корректный вес", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        WriteDataWeight(weight.getText().toString());
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_cal_weight);
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });
        desired_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.calculator_weigth_button_dialog, null);
                EditText weight = existProduct.findViewById(R.id.weight);
                TextView textView = existProduct.findViewById(R.id.text_xml);
                textView.setText("Изменить желаемый вес");
                weight.setHint("Кг");
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(weight.getText())){
                            Toast.makeText(getContext(), "Введите ваш вес в кг", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Float.parseFloat(weight.getText().toString()) > 1000 || Float.parseFloat(weight.getText().toString()) < 10){
                            Toast.makeText(getContext(), "Введите корректный вес", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put("desired_weight", weight.getText().toString());
                        users.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                navController.navigate(R.id.nav_cal_weight);
                            }
                        });
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });

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
                        if(TextUtils.isEmpty(weight.getText())){
                            Toast.makeText(getContext(), "Введите ваш вес в кг", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Float.parseFloat(weight.getText().toString()) > 1000 || Float.parseFloat(weight.getText().toString()) < 10){
                            Toast.makeText(getContext(), "Введите корректный вес", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        WriteDataWeight(weight.getText().toString());
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_cal_weight);
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });

        return root;
    }

    private void Adaptive() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;
        int w = metrics.widthPixels;

        Log.d(TAG, String.valueOf(h));

        //Основний лінер який об'єднує нижні лінери//

        LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h / 7);
        layout_params.setMargins(10, 10, 10, 10);
        linear_weight.setWeightSum(2);
        linear_weight.setLayoutParams(layout_params);

        //Лінери які містять текст і їх 2////
        LinearLayout.LayoutParams layout_top = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h / 7);
        layout_top.weight = 1;
        layout_top.setMargins(w / 84, 10, w / 84, 10);
        //Лівий блок
        layout_left_xlm.setGravity(CENTER);
        layout_left_xlm.setLayoutParams(layout_top);
        //Правий блок
        desired_weight.setGravity(CENTER);
        desired_weight.setLayoutParams(layout_top);
        //Параметра TextView
        LinearLayout.LayoutParams layout_top_text = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_top_text.setMargins(w / 84, 0, w / 84, 0);
        //Встановлення внутрішніх відступів
        text_current_weight_xml.setPadding(0, 0, 0, 0);
        text_weight_xml.setPadding(0,0,0,0);
        //Встановлення параметрів TextView
        text_weight_xml.setLayoutParams(layout_top_text);
        text_current_weight_xml.setLayoutParams(layout_top_text);
        weight.setLayoutParams(layout_top_text);
        current_weight.setLayoutParams(layout_top_text);
        //Умова яка змінює розмір тексту верхніх Layout залежно від екрану
        if (h < 2400) {
            text_weight_xml.setTextSize(16);
            text_current_weight_xml.setTextSize(16);
            weight.setTextSize(16);
            current_weight.setTextSize(16);
        } else if (h < 1400) {
            text_weight_xml.setTextSize(14);
            text_current_weight_xml.setTextSize(14);
            weight.setTextSize(14);
            current_weight.setTextSize(14);
        }


//text_weight_xml text_current_weight_xml  weight current_weight

        //Параметри Графіка
        LinearLayout.LayoutParams graph_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h / 3);
        graph_params.setMargins(0, 20, 0, 10);
        graphView.setPadding(10,10,10,10);
        graphView.setLayoutParams(graph_params);
        //Параметри кнопки
        RelativeLayout.LayoutParams but_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h / 16);
        but_params.setMargins(h / 32, h / 84, 32, h / 84);
        but_params.addRule(2, R.id.bar);
        add_weight.setPadding(0,0,0,0);
        add_weight.setLayoutParams(but_params);

        //Умова яка змінює розмір тексту Кнопки залежно від екрану
        if (h < 2400) {
            add_weight.setTextSize(16);
        } else if (h < 1400) {
            add_weight.setTextSize(14);
        }


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

    @Override
    public void onStop() {
        super.onStop();
        query.close();
        myWeight.close();
    }



    private void FindView(View root) {
        linear_weight = root.findViewById(R.id.linear_weight);
        layout_left_xlm = root.findViewById(R.id.layout_left_xlm);
        desired_weight = root.findViewById(R.id.desired_weight);
        bar = root.findViewById(R.id.bar);
        add_weight = root.findViewById(R.id.add_weight);
        weight = root.findViewById(R.id.my_weight);
        current_weight = root.findViewById(R.id.current_weight);
        graphView = root.findViewById(R.id.graph);
        listView = root.findViewById(R.id.list_item);
        text_current_weight_xml = root.findViewById(R.id.text_current_weight_xml);
        text_weight_xml = root.findViewById(R.id.text_weight_xml);



    }
}