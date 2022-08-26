package com.sharkit.nextmonday.main_menu.diary.entity.recipe;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecipeDTO {
    @Embedded
    private RecipeTemplateDTO template;

    @Relation(parentColumn = "id",
            entityColumn = "template_id")
    private List<RecipeItemDTO> items;
}
