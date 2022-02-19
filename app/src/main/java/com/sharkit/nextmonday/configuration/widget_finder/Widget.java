package com.sharkit.nextmonday.configuration.widget_finder;

import android.view.View;

import com.sharkit.nextmonday.configuration.widget_finder.impl.WidgetFinder;
import com.sharkit.nextmonday.configuration.widget_finder.widget.ButtonWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.TextViewWidget;


public class Widget implements WidgetFinder {

    private final View view;

    private Widget(View view) {
        this.view = view;
    }

    public static Widget findByView(View view) {
        return new Widget(view);
    }

    @Override
    public ButtonWidget getButton() {
        return new ButtonWidget(view);
    }

    @Override
    public EditTextWidget getTextField() {
            return new EditTextWidget(view);
    }

    @Override
    public TextViewWidget getTextView() {
        return new TextViewWidget(view);
    }

}
