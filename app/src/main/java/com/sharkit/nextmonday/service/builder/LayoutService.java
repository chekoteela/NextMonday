package com.sharkit.nextmonday.service.builder;

import android.view.View;

public interface LayoutService {
    LayoutService writeToField();
    LayoutService findById(View root);
    LayoutService setAdaptive();
    LayoutService activity();
}
