package com.sharkit.nextmonday.main_menu.diary.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecipeDTO {
    @Embedded public RecipeTemplateDTO template;

    @Relation(
            parentColumn = "id",
            entityColumn = "template_id"
    )
    public List<RecipeItemDTO> items;
}
