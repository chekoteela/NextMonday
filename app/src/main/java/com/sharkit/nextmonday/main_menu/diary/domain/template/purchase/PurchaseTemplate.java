package com.sharkit.nextmonday.main_menu.diary.domain.template.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PurchaseTemplate {

    private Long id;
    private String date;
    private String text;
}
