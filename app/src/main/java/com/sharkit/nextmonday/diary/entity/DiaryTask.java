package com.sharkit.nextmonday.diary.entity;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.configuration.annotation.Collection;
import com.sharkit.nextmonday.configuration.annotation.Id;
import com.sharkit.nextmonday.diary.enums.DayOfAlarm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
public class DiaryTask implements Serializable {

    @Id
    private final String id = UUID.randomUUID().toString();
    private final String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private int week;
    private String date;
    private String nameOfTask;
    private String description;
    private List<DayOfAlarm> daysOfAlarm;
    private int hour;
    private int minute;
    private boolean status;
}
