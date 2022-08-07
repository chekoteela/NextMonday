package com.sharkit.nextmonday.main_menu.diary.transformer;

import static com.sharkit.nextmonday.main_menu.diary.transformer.NotateTemplateTransformer.toRecipeTemplate;
import static com.sharkit.nextmonday.main_menu.diary.transformer.RecipeItemTransformer.toRecipeItems;

import com.sharkit.nextmonday.main_menu.diary.domain.template.Recipe;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeTransformer {

    public static Recipe toRecipe(RecipeDTO dto) {
        return Recipe.builder()
                .recipeTemplate(toRecipeTemplate(dto.getTemplate()))
                .recipeItems(toRecipeItems(dto.getItems()))
                .build();
    }
}
