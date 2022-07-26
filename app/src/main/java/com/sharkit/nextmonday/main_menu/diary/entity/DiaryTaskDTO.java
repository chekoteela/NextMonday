package com.sharkit.nextmonday.main_menu.diary.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(tableName = "diary_task")
@AllArgsConstructor
public class DiaryTaskDTO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "name_of_task")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "time_of_alarm")
    private Long timeForRepeat;

    @ColumnInfo(name = "days_of_repeat")
    private byte[] daysOfRepeat;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "is_completed")
    private Boolean completed;

    @ColumnInfo(name = "repeated")
    private Boolean repeated;
}
