package com.sharkit.nextmonday.configuration.adaptive;

import android.widget.Button;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.adaptive.widget_adaptive.linear.ButtonLinearAdaptive;
import com.sharkit.nextmonday.configuration.adaptive.widget_adaptive.linear.EditTextLinearAdaptive;

public class Adaptive {

    public static ButtonLinearAdaptive setLinearButtonAdaptive(Button button){
        return ButtonLinearAdaptive.setButton(button);
    }

    public static EditTextLinearAdaptive setLinearEditTextAdaptive(EditText editText){
        return EditTextLinearAdaptive.setEditText(editText);
    }

}
