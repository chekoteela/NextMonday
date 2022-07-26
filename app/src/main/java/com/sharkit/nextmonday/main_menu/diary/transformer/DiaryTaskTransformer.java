package com.sharkit.nextmonday.main_menu.diary.transformer;

import android.util.Log;

import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryTaskTransformer {

    private static final String TAG = DiaryTaskTransformer.class.getCanonicalName();

    public static DiaryTaskDTO toDiaryTaskDTO(DiaryTask diaryTask, Calendar calendar) {

        return DiaryTaskDTO.builder()
                .id(diaryTask.getId())
                .daysOfRepeat(toByteArray(diaryTask.getRepeats()))
                .name(diaryTask.getName())
                .description(diaryTask.getDescription())
                .timeForRepeat(calendar.getTimeInMillis())
                .date(DateFormat.getDateInstance().format(calendar.getTime()))
                .completed(diaryTask.getCompleted())
                .build();
    }

    public static List<DiaryTask> toDiaryTasks(List<DiaryTaskDTO> list) {
        return list.stream()
                .map(DiaryTaskTransformer::toDiaryTask)
                .collect(Collectors.toList());
    }

    private static DiaryTask toDiaryTask(DiaryTaskDTO diaryTaskDTO) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(diaryTaskDTO.getTimeForRepeat());

        return DiaryTask.builder()
                .id(diaryTaskDTO.getId())
                .description(diaryTaskDTO.getDescription())
                .name(diaryTaskDTO.getName())
                .timeForRepeat(diaryTaskDTO.getTimeForRepeat())
                .repeats(toList(diaryTaskDTO.getDaysOfRepeat()))
                .completed(diaryTaskDTO.getCompleted())
                .build();
    }

    private static List<DayOfRepeat> toList(byte[] bytes) {
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream is = new ObjectInputStream(in)) {
            return (List<DayOfRepeat>) is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
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
