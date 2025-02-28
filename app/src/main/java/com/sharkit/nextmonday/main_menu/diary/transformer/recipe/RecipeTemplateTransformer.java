package com.sharkit.nextmonday.main_menu.diary.transformer.recipe;

import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.RecipeTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeTemplateDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeTemplateTransformer {

    public static RecipeTemplateDTO toRecipeTemplateDTO(final RecipeTemplate recipeTemplate) {
        return RecipeTemplateDTO.builder()
                .imageCod(recipeTemplate.getImageCod())
                .date(recipeTemplate.getDate())
                .text(recipeTemplate.getDescription())
                .templateId(recipeTemplate.getTemplateId())
                .build();
    }

    public static RecipeTemplate toRecipeTemplate(final RecipeTemplateDTO recipeTemplateDTO) {
        return RecipeTemplate.builder()
                .imageCod(recipeTemplateDTO.getImageCod())
                .date(recipeTemplateDTO.getDate())
                .description(recipeTemplateDTO.getText())
                .templateId(recipeTemplateDTO.getTemplateId())
                .build();
    }
}
