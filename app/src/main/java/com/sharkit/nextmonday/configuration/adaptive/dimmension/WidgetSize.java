package com.sharkit.nextmonday.configuration.adaptive.dimmension;

import android.content.Context;

public interface WidgetSize {
    WidgetSize setContext(Context context);
    int getHeight();
    int getWidth();
    int getMarginTop();
    int getMarginBottom();
    int getMarginLeft();
    int getMarginRight();
}
