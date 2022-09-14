package com.sharkit.nextmonday.main_menu.diary.transformer.recipe;

import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeTemplateTransformer.toRecipeTemplate;
import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeItemTransformer.toRecipeItems;

import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.Recipe;
import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeTransformer {

    public static Recipe toRecipe(final RecipeDTO dto) {
        return Recipe.builder()
                .recipeTemplate(toRecipeTemplate(dto.getTemplate()))
                .recipeItems(toRecipeItems(dto.getItems()))
                .build();
    }
}
