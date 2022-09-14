package com.sharkit.nextmonday.main_menu.diary.transformer.recipe;

import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.RecipeItem;
import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeItemDTO;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeItemTransformer {

    public static List<RecipeItem> toRecipeItems(final List<RecipeItemDTO> recipeItemDTOS) {
        return recipeItemDTOS.stream()
                .map(RecipeItemTransformer::toRecipeItem)
                .collect(Collectors.toList());
    }

    public static RecipeItemDTO toRecipeItemDTO(final RecipeItem item) {
        return RecipeItemDTO.builder()
                .templateId(item.getTemplateId())
                .name(item.getName())
                .description(item.getDescription())
                .id(item.getId())
                .build();
    }

    public static RecipeItem toRecipeItem(final RecipeItemDTO item) {
        return RecipeItem.builder()
                .templateId(item.getTemplateId())
                .name(item.getName())
                .description(item.getDescription())
                .id(item.getId())
                .build();
    }
}
