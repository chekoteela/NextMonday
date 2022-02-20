package com.sharkit.nextmonday.configuration.adaptive.transformer;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.adaptive.Adaptive;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;

public class AdaptiveTransformer {

    public static void toDefaultButtonAdaptive(Button button, TemplateAdaptive adaptive, Context context, int gravity){
        Adaptive.setLinearButtonAdaptive(button)
                .setTemplateContext(adaptive, context)
                .setParams()
                .setMargins()
                .setLayoutGravity(gravity)
                .build();
    }
    public static void toDefaultEditTextAdaptive(EditText editText, TemplateAdaptive adaptive, Context context, int gravity){
        Adaptive.setLinearEditTextAdaptive(editText)
                .setTemplateContext(adaptive, context)
                .setParams()
                .setMargins()
                .setLayoutGravity(gravity)
                .build();
    }

}
