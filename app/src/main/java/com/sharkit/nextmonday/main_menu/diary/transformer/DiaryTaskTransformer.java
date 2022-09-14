package com.sharkit.nextmonday.main_menu.diary.transformer;

import static com.sharkit.nextmonday.main_menu.diary.transformer.ByteArrayTransformer.toByteArray;
import static com.sharkit.nextmonday.main_menu.diary.transformer.ByteArrayTransformer.toObject;

import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;
import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryTaskTransformer {

    public static DiaryTaskDTO toDiaryTaskDTO(final DiaryTask diaryTask) {

        return DiaryTaskDTO.builder()
                .id(diaryTask.getId())
                .userId(diaryTask.getUserId())
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

    public static List<DiaryTask> toDiaryTasks(final List<DiaryTaskDTO> list) {
        return list.stream()
                .map(DiaryTaskTransformer::toDiaryTask)
                .collect(Collectors.toList());
    }

    private static DiaryTask toDiaryTask(final DiaryTaskDTO diaryTaskDTO) {
        return DiaryTask.builder()
                .id(diaryTaskDTO.getId())
                .description(diaryTaskDTO.getDescription())
                .name(diaryTaskDTO.getName())
                .timeForRepeat(diaryTaskDTO.getTimeForRepeat())
                .repeats((List<DayOfRepeat>) toObject(diaryTaskDTO.getDaysOfRepeat()))
                .completed(diaryTaskDTO.getCompleted())
                .repeated(diaryTaskDTO.getRepeated())
                .date(diaryTaskDTO.getDate())
                .groupId(diaryTaskDTO.getGroupId())
                .alarm(diaryTaskDTO.getAlarm())
                .build();
    }

    public static DiaryTask toDiaryTask(final DiaryTask diaryTask, final Calendar calendar) {
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
