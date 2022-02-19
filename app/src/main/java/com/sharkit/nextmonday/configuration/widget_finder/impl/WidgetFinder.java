package com.sharkit.nextmonday.configuration.widget_finder.impl;

import com.sharkit.nextmonday.configuration.widget_finder.widget.ButtonWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.TextViewWidget;

public interface WidgetFinder {
    ButtonWidget getButton();
    EditTextWidget getTextField();
    TextViewWidget getTextView();
}
