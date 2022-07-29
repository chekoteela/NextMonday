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
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryTaskTransformer {

    private static final String TAG = DiaryTaskTransformer.class.getCanonicalName();

    public static DiaryTaskDTO toDiaryTaskDTO(DiaryTask diaryTask) {

        return DiaryTaskDTO.builder()
                .id(diaryTask.getId())
                .daysOfRepeat(toByteArray(diaryTask.getRepeats()))
                .name(diaryTask.getName())
                .description(diaryTask.getDescription())
                .timeForRepeat(Optional.ofNullable(diaryTask.getTimeForRepeat()).orElse(Calendar.getInstance().getTimeInMillis()))
                .date(diaryTask.getDate())
                .completed(Optional.ofNullable(diaryTask.getCompleted()).orElse(Boolean.FALSE))
                .repeated(Optional.ofNullable(diaryTask.getRepeated()).orElse(Boolean.FALSE))
                .alarm(Optional.ofNullable(diaryTask.getAlarm()).orElse(Boolean.FALSE))
                .groupId(diaryTask.getGroupId())
                .build();
    }

    public static List<DiaryTask> toDiaryTasks(List<DiaryTaskDTO> list) {
        return list.stream()
                .map(DiaryTaskTransformer::toDiaryTask)
                .collect(Collectors.toList());
    }

    private static DiaryTask toDiaryTask(DiaryTaskDTO diaryTaskDTO) {
        return DiaryTask.builder()
                .id(diaryTaskDTO.getId())
                .description(diaryTaskDTO.getDescription())
                .name(diaryTaskDTO.getName())
                .timeForRepeat(diaryTaskDTO.getTimeForRepeat())
                .repeats(toList(diaryTaskDTO.getDaysOfRepeat()))
                .completed(diaryTaskDTO.getCompleted())
                .repeated(diaryTaskDTO.getRepeated())
                .date(diaryTaskDTO.getDate())
                .groupId(diaryTaskDTO.getGroupId())
                .alarm(diaryTaskDTO.getAlarm())
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

    public static DiaryTask toDiaryTask(DiaryTask diaryTask, Calendar calendar) {
        return DiaryTask.builder()
                .timeForRepeat(calendar.getTimeInMillis())
                .date(DateFormat.getDateInstance().format(calendar.getTime()))
                .name(diaryTask.getName())
                .repeats(diaryTask.getRepeats())
                .description(diaryTask.getDescription())
                .groupId(diaryTask.getGroupId())
                .build();
    }
}
