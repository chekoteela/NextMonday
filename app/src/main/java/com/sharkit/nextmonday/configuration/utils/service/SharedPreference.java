package com.sharkit.nextmonday.configuration.utils.service;

import static com.sharkit.nextmonday.main_menu.diary.transformer.ByteArrayTransformer.toByteArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.sharkit.nextmonday.R;

import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SharedPreference {

    private final Context context;
    private final String sharedName;

    public void setImage(String imageCod ,Drawable image){
        SharedPreferences.Editor editor = getEditor();
        editor.putString(imageCod, Base64.encodeToString(toByteArray(image), Base64.DEFAULT));
    }

    public Bitmap getImage(String imageCode){
        final byte[] imageByte = Base64.decode(getSharedPreferences().getString(imageCode,""), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        return Optional.ofNullable(bitmap).orElse(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu_camera));
    }

    public void setValueShared(Map<String, String> map) {
        SharedPreferences.Editor editor = getEditor();
        map.keySet().forEach(key -> editor.putString(key, map.get(key)));
        editor.apply();
    }

    public Map<String, ?> getValueShared() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getAll();
    }

    private SharedPreferences.Editor getEditor(){
        return context.getSharedPreferences(sharedName, Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
    }
}
