package com.sharkit.nextmonday.Exception;

import android.content.Context;
import android.widget.Toast;

public class CustomToastException extends Exception{
    private Context context;
    public CustomToastException(Context context, String message){
            this.context = context;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
