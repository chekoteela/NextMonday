package com.sharkit.nextmonday.diary.entity;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.configuration.annotation.Collection;
import com.sharkit.nextmonday.configuration.annotation.Id;
import com.sharkit.nextmonday.diary.enums.DayOfAlarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Collection(collection = "diary_task")
public class DiaryTask {

    @Id
    private final String id = UUID.randomUUID().toString();
    private final String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private final String date = SimpleDateFormat.getDateInstance().format(Calendar.getInstance().getTimeInMillis());
    private final int week = SimpleDateFormat.WEEK_OF_YEAR_FIELD;
    private String nameOfTask;
    private String description;
    private List<DayOfAlarm> daysOfAlarm;
    private int hour;
    private int minute;
    private boolean status;
}
