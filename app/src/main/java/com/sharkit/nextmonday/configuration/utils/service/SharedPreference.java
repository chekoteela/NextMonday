package com.sharkit.nextmonday.configuration.utils.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SharedPreference {

    private final Context context;
    private final String sharedName;

    public void setValueShared(Map<String, String> map) {
        SharedPreferences.Editor editor = getEditor();
        map.keySet().forEach(key -> editor.putString(key, map.get(key)));
        editor.apply();
    }

    public void setJSON(String json) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(sharedName, json);
        editor.commit();
    }

    public String getJSON() {
        final SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(sharedName, null);
    }

    public Map<String, ?> getValueShared() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getAll();
    }

    private SharedPreferences.Editor getEditor() {
        return context.getSharedPreferences(sharedName, Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
    }
}
