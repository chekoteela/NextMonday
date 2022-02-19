package com.sharkit.nextmonday.configuration.adaptive.service;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.adaptive.Adaptive;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

import java.util.Arrays;
import java.util.List;

public class AdaptiveService {

    public static void setMainActivityAdaptive(TemplateAdaptive adaptive, Activity activity, Widget widget){
        List<Button> buttons = Arrays.asList(widget.getButton().getSignIn(), widget.getButton().getCreateAccount());
        buttons.forEach(b -> Adaptive.setLinearButtonAdaptive(b)
                .setTemplateContext(adaptive, activity)
                .setParams()
                .setMargins()
                .setLayoutGravity(Gravity.CENTER)
                .build());

        List<EditText> editTexts = Arrays.asList(widget.getTextField().getEmail(), widget.getTextField().getPassword());
        editTexts.forEach(e -> Adaptive.setLinearEditTextAdaptive(e)
                .setTemplateContext(adaptive, activity)
                .setParams()
                .setMargins()
                .setLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .build());
    }
}
