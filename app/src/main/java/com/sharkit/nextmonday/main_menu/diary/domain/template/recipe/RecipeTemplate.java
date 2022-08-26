package com.sharkit.nextmonday.main_menu.diary.domain.template.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTemplate {

    private Long templateId;
    private String date;
    private String description;
    private String imageCod;
}
