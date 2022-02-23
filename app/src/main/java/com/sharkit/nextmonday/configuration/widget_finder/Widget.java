package com.sharkit.nextmonday.configuration.widget_finder;

import android.view.View;

import com.sharkit.nextmonday.configuration.widget_finder.impl.WidgetFinder;
import com.sharkit.nextmonday.configuration.widget_finder.widget.AdViewWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.ButtonWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.CheckBoxWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.ImageViewWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.SwitchWidget;
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

    @Override
    public ImageViewWidget getImageView() {
        return new ImageViewWidget(view);
    }

    @Override
    public CheckBoxWidget getCheckBox() {
        return new CheckBoxWidget(view);
    }

    @Override
    public SwitchWidget getSwitch() {
        return new SwitchWidget(view);
    }

    @Override
    public AdViewWidget getAdView() {
        return new AdViewWidget(view);
    }

}
