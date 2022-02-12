package com.sharkit.nextmonday.service.calculator.weight;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.ACCEPT;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.UPDATE_WEIGHT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdView;
import com.jjoe64.graphview.GraphView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.calculator.WeightAdapter;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.db.firestore.calculator.WeightFirebase;
import com.sharkit.nextmonday.db.sqlite.calculator.weight.WeightDataService;
import com.sharkit.nextmonday.entity.calculator.Weight;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class WeightService implements LayoutService {
    private Context context;
    private TextView currentWeight, desiredWeight;
    private GraphView graphView;
    private ListView listView;
    private Button addWeight;
    private AdView adView;

    @Override
    public LayoutService writeToField() {
        Configuration.showAdView(adView);
        currentWeight.setText(String.valueOf(new WeightDataService(context).getWeight()));
        getWeightList();
        return this;
    }

    private void getWeightList() {
        listView.setAdapter(new WeightAdapter(new WeightDataService(context).getAll(), context));
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @SuppressLint("InflateParams")
    @Override
    public LayoutService activity() {
        addWeight.setOnClickListener(v -> createWeightDialog());
        getGraph();
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        currentWeight = root.findViewById(R.id.current_weight_xml);
        desiredWeight = root.findViewById(R.id.desired_weight_xml);
        graphView = root.findViewById(R.id.graph_view_xml);
        listView = root.findViewById(R.id.list_weight_xml);
        addWeight = root.findViewById(R.id.add_weight_xml);
        adView = root.findViewById(R.id.adView);
        return this;
    }
    private void createWeightDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.calculator_weigth_alert_dialog, null);
        EditText editText = view.findViewById(R.id.weight_xml);
        dialog.setOnCancelListener(DialogInterface::dismiss);
        dialog.setPositiveButton(ACCEPT, (dialog1, which) -> {
            Weight weight = new Weight();
            weight.setWeight(Float.parseFloat(editText.getText().toString()));
            weight.setDate(new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()));
            new WeightDataService(context)
                    .create(weight);
            new WeightFirebase()
                    .create(weight)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(context, UPDATE_WEIGHT, Toast.LENGTH_SHORT).show();
                        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_cal_weight);
                    });
        });
        dialog.setView(view);
        dialog.show();
    }

    private void getGraph(){

    }
}
