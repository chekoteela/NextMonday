package com.sharkit.nextmonday.main_menu.diary.transformer;

import android.util.Log;

import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryTaskTransformer {

    private static final String TAG = DiaryTaskTransformer.class.getCanonicalName();

    public static DiaryTaskDTO toDiaryTaskDTO(DiaryTask diaryTask, Calendar calendar) {

        return DiaryTaskDTO.builder()
                .daysOfRepeat(toByteArray(diaryTask.getRepeats()))
                .name(diaryTask.getName())
                .description(diaryTask.getDescription())
                .timeForRepeat(getTimeInMillis(calendar, diaryTask.getHour(), diaryTask.getMinute()))
                .date(DateFormat.getDateInstance().format(calendar))
                .build();
    }

    private static Long getTimeInMillis(Calendar calendar, Integer hour, Integer minute) {
        if (hour == null || minute == null) {
            return 0L;
        } else {
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTimeInMillis();
        }
    }

    private static byte[] toByteArray(List<DayOfRepeat> repeats) {
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
