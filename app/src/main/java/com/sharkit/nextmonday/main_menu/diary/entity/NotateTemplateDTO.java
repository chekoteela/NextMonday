package com.sharkit.nextmonday.main_menu.diary.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(tableName = "diary_notate_template")
public class NotateTemplateDTO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long templateId;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "text")
    private String text;

}
