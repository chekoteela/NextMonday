package com.sharkit.nextmonday.main_menu.diary.domain.template.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PurchaseItem {

    private Long id;
    private Long templateId;
    private String name;
    private String description;
    private Boolean complete;
}
