package com.sharkit.nextmonday.main_menu.diary.transformer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.sharkit.nextmonday.configuration.exception.ByteConvertationException;

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

    public static Object toObject(final byte[] bytes) {
        try (final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final ObjectInputStream is = new ObjectInputStream(in)) {
            return is.readObject();
        } catch (final ClassNotFoundException | IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new ByteConvertationException(e.getMessage(), e);
        }
    }

    public static byte[] toByteArray(final Object repeats) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream();
             final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(repeats);
            return bos.toByteArray();
        } catch (final IOException e) {
            Log.i(TAG, e.getMessage(), e);
            throw new ByteConvertationException(e.getMessage(), e);
        }
    }

    public static byte[] toByteArray(final Drawable drawable){
        final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
