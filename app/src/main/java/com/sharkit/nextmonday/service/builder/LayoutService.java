package com.sharkit.nextmonday.service.builder;

import android.view.View;

public interface LayoutService {
    LayoutService writeToField();
    LayoutService setAdaptive();
    LayoutService activity();
    LayoutService findById(View root);
}
