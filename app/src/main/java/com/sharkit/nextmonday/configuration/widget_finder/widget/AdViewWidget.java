package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;

import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;

public class AdViewWidget {

    private final View view;

    public AdViewWidget(View view) {
        this.view = view;
    }

    public AdView getAdView() {
        return view.findViewById(R.id.ad_view_xml);
    }

}
