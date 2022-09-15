package com.sharkit.nextmonday.configuration.utils.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SharedPreference {

    private final Context context;
    private final String sharedName;

    public void clear() {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.clear();
        editor.apply();
    }

    public void setValueShared(final Map<String, String> map) {
        final SharedPreferences.Editor editor = this.getEditor();
        map.keySet().forEach(key -> editor.putString(key, map.get(key)));
        editor.apply();
    }

    public void setJSON(final String json) {
        final SharedPreferences.Editor editor = this.getEditor();
        editor.putString(this.sharedName, json);
        editor.commit();
    }

    public String getJSON() {
        final SharedPreferences sharedPreferences = this.getSharedPreferences();
        return sharedPreferences.getString(this.sharedName, null);
    }

    public Map<String, ?> getValueShared() {
        final SharedPreferences sharedPreferences = this.getSharedPreferences();
        return sharedPreferences.getAll();
    }

    private SharedPreferences.Editor getEditor() {
        return this.context.getSharedPreferences(this.sharedName, Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPreferences() {
        return this.context.getSharedPreferences(this.sharedName, Context.MODE_PRIVATE);
    }
}
