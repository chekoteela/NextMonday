package com.sharkit.nextmonday.main_menu.diary.transformer;

import com.sharkit.nextmonday.main_menu.diary.domain.template.RecipeItem;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeItemDTO;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeItemTransformer {

    public static List<RecipeItem> toRecipeItems(List<RecipeItemDTO> recipeItemDTOS) {
        return recipeItemDTOS.stream()
                .map(RecipeItemTransformer::toRecipeItem)
                .collect(Collectors.toList());
    }

    public static RecipeItemDTO toRecipeItemDTO(RecipeItem item) {
        return RecipeItemDTO.builder()
                .templateId(item.getTemplateId())
                .name(item.getName())
                .description(item.getDescription())
                .id(item.getId())
                .build();
    }

    public static RecipeItem toRecipeItem(RecipeItemDTO item) {
        return RecipeItem.builder()
                .templateId(item.getTemplateId())
                .name(item.getName())
                .description(item.getDescription())
                .id(item.getId())
                .build();
    }
}
