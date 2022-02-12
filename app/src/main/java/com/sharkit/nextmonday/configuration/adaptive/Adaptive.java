package com.sharkit.nextmonday.configuration.adaptive;

import android.widget.Button;

import com.sharkit.nextmonday.configuration.adaptive.button.ButtonAdaptive;

public class Adaptive {

    public static ButtonAdaptive setButton(Button button){
        return new ButtonAdaptive(button);
    }
}
