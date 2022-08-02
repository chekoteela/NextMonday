package com.sharkit.nextmonday.main_menu.diary.transformer;

import android.util.Log;

import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NotateTransformer {

    private static final String TAG = DiaryTaskTransformer.class.getCanonicalName();

    public static NotateDTO toNotateDTO(Notate notate) {
        return NotateDTO.builder()
                .id(notate.getId())
                .name(notate.getName())
                .templateId(notate.getTemplateId())
                .notateType(toByteArray(notate.getNotateType()))
                .type(toByteArray(notate.getTemplateType()))
                .build();
    }

    private static Object toObject(byte[] bytes) {
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream is = new ObjectInputStream(in)) {
            return is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private static byte[] toByteArray(Object repeats) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(repeats);
            return bos.toByteArray();
        } catch (IOException e) {
            Log.i(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
