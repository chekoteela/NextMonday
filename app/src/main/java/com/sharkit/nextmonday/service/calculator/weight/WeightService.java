package com.sharkit.nextmonday.service.calculator.weight;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jjoe64.graphview.GraphView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.service.builder.LayoutService;

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
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
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
}
