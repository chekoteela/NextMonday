package com.sharkit.nextmonday.main_menu.diary.domain.template;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Recipe {

    private RecipeTemplate recipeTemplate;
    private List<RecipeItem> recipeItems;
}
