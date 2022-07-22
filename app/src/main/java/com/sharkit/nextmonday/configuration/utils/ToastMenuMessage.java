package com.sharkit.nextmonday.configuration.utils;

import android.content.Context;
import android.widget.Toast;

import com.sharkit.nextmonday.NextMondayActivity;

public class ToastMenuMessage {

    public static void trowToastMessage() {
        Toast.makeText(NextMondayActivity.getContext(), "some text", Toast.LENGTH_SHORT).show();
    }
}
