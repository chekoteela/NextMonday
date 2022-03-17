package com.sharkit.nextmonday.configuration.constant;

import android.content.Context;

import com.sharkit.nextmonday.R;

public class ButtonText {

    public static String SEND(Context context){
        return context.getString(R.string.button_send);
    }

    public static String AGREE_POLICY(Context context) {
        return context.getString(R.string.button_agree_policy);
    }

    public static String ACCEPT(Context context){
        return context.getString(R.string.button_accept);
    }

    public static String DELETE(Context context){
        return context.getString(R.string.button_delete);
    }

    public static String CHANGE(Context context){
        return context.getString(R.string.button_change);
    }
}
