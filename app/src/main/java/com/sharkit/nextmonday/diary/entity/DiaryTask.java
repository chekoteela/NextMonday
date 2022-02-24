package com.sharkit.nextmonday.diary.entity;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.diary.enums.DayOfAlarm;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DiaryTask {

    private final String id = UUID.randomUUID().toString();
    private final String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private String nameOfTask;
    private String description;
    private List<DayOfAlarm> daysOfAlarm;
    private int hour;
    private int minute;
    private String date;
}
