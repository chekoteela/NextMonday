package com.sharkit.nextmonday.main_menu.diary.entity.purchase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "diary_purchase_template")
public class PurchaseTemplateDTO {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "text")
    private String text;
}
