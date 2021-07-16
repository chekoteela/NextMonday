package com.sharkit.nextmonday.Exception;

import android.content.Context;
import android.widget.Toast;

public class CustomToastComplete extends  Exception{
    private Context context;
    public CustomToastComplete(Context context, String message){
        this.context = context;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
