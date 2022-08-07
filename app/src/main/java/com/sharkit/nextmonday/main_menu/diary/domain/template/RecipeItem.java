package com.sharkit.nextmonday.main_menu.diary.domain.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeItem {

    private Long id;
    private Long templateId;
    private String name;
    private String description;
}
