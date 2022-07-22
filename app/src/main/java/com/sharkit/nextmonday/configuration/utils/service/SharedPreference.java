package com.sharkit.nextmonday.configuration.utils.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreference {

    public static final String USER_PREFERENCES = "user_preferences";
    public static final String USER_ID = "user_id";
    private final Context context;
    private final String sharedName;

    private SharedPreference(Context context, String sharedName){
        this.sharedName = sharedName;
        this.context = context;
    }

    public static SharedPreference getPreferences(Context context, String sharedName){
        return new SharedPreference(context, sharedName);
    }

    public void setValueShared(Map<String, String> map){
        SharedPreferences.Editor editor = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE).edit();
        map.keySet().forEach(key -> editor.putString(key, map.get(key)));
        editor.apply();
    }

    public Map<String, ?> getValueShared(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }
}
