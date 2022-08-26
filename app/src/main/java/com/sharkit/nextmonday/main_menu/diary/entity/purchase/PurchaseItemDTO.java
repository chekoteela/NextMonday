package com.sharkit.nextmonday.main_menu.diary.entity.purchase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity(tableName = "purchase_item")
public class PurchaseItemDTO {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "template_id")
    private Long templateId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "status")
    private Boolean status;
}
