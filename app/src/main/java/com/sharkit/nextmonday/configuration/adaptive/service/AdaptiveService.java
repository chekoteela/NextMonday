package com.sharkit.nextmonday.configuration.adaptive.service;

import static com.sharkit.nextmonday.configuration.adaptive.transformer.AdaptiveTransformer.toDefaultButtonAdaptive;
import static com.sharkit.nextmonday.configuration.adaptive.transformer.AdaptiveTransformer.toDefaultEditTextAdaptive;

import android.app.Activity;
import android.view.Gravity;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

import java.util.Arrays;
import java.util.List;

public class AdaptiveService {

    public static void setMainActivityAdaptive(TemplateAdaptive adaptive, Activity activity, Widget widget){
        Arrays.asList(widget.getButton().getSignIn(), widget.getButton().getCreateAccount())
        .forEach(b -> toDefaultButtonAdaptive(b, adaptive, activity.getBaseContext(), Gravity.CENTER));

        List<EditText> editTexts = Arrays.asList(widget.getTextField().getEmail(), widget.getTextField().getPassword());
        editTexts.forEach(e -> toDefaultEditTextAdaptive(e, adaptive, activity.getBaseContext(), Gravity.CENTER));
    }
    public static void setRegistrationLayoutAdaptive(TemplateAdaptive adaptive, Activity activity, Widget widget){
        Arrays.asList(widget.getTextField().getUserName(),
                widget.getTextField().getUserLastName(),
                widget.getTextField().getEmail(),
                widget.getTextField().getPassword())
                .forEach(e -> toDefaultEditTextAdaptive(e, adaptive, activity.getBaseContext(), Gravity.CENTER));

        Arrays.asList(widget.getButton().getCreateAccount(),
                widget.getButton().getSignIn())
                .forEach(b -> toDefaultButtonAdaptive(b, adaptive, activity.getBaseContext(), Gravity.CENTER));
    }

}
