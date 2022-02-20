package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.ImageView;

import com.sharkit.nextmonday.R;

public class ImageViewWidget {

    private final View view;

    public ImageViewWidget(View view) {
        this.view = view;
    }
    public ImageView getGoogle() {
        return view.findViewById(R.id.google_xml);
    }
}
