package com.sharkit.nextmonday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class NextMondayActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.start_activity);
    }


    public static Context getContext() {
        return context;
    }
}