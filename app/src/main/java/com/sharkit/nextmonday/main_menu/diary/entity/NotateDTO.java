package com.sharkit.nextmonday.main_menu.diary.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(tableName = "diary_notate")
@AllArgsConstructor
public class NotateDTO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "parent_folder_id")
    private Long parentFolderId;

    @ColumnInfo(name = "template_type")
    private byte[] templateType;

    @ColumnInfo(name = "notate_type")
    private byte[] notateType;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "template_id")
    private Long templateId;
}
