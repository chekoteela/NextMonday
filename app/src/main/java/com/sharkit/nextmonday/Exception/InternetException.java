package com.sharkit.nextmonday.Exception;

import android.content.Context;
import android.widget.Toast;

public class InternetException extends Exception{
    private Context context;
    public InternetException(Context context){
        this.context = context;
        Toast.makeText(context, "Подключение к интернету отстствует", Toast.LENGTH_SHORT).show();
    }
}
