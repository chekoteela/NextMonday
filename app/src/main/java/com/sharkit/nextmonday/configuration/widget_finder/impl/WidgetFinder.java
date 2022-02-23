package com.sharkit.nextmonday.configuration.widget_finder.impl;

import com.sharkit.nextmonday.configuration.widget_finder.widget.AdViewWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.ButtonWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.CheckBoxWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.EditTextWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.ExpandableListWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.ImageViewWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.LinearLayoutWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.RadioButtonWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.RadioGroupWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.SwitchWidget;
import com.sharkit.nextmonday.configuration.widget_finder.widget.TextViewWidget;

public interface WidgetFinder {
    ButtonWidget getButton();
    EditTextWidget getTextField();
    TextViewWidget getTextView();
    ImageViewWidget getImageView();
    CheckBoxWidget getCheckBox();
    SwitchWidget getSwitch();
    AdViewWidget getAdView();
    ExpandableListWidget getExpandableList();
    RadioButtonWidget getRadioButton();
    LinearLayoutWidget getLinearLayout();
    RadioGroupWidget getRadioGroup();
}
