package com.sharkit.nextmonday.main_menu.diary.transformer;

import com.sharkit.nextmonday.main_menu.diary.domain.template.RecipeTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeTemplateDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotateTemplateTransformer {

    public static RecipeTemplateDTO toNotateTemplateDTO(RecipeTemplate recipeTemplate) {
        return RecipeTemplateDTO.builder()
                .date(recipeTemplate.getDate())
                .text(recipeTemplate.getText())
                .templateId(recipeTemplate.getTemplateId())
                .build();
    }
}
