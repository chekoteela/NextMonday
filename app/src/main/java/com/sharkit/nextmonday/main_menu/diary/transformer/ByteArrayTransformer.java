package com.sharkit.nextmonday.main_menu.diary.transformer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteArrayTransformer {

    private static final String TAG = ByteArrayTransformer.class.getCanonicalName();

    public static Object toObject(byte[] bytes) {
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream is = new ObjectInputStream(in)) {
            return is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] toByteArray(Object repeats) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(repeats);
            return bos.toByteArray();
        } catch (IOException e) {
            Log.i(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] toByteArray(Drawable drawable){
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
