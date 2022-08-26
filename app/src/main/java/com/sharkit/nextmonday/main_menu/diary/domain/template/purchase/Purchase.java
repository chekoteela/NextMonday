package com.sharkit.nextmonday.main_menu.diary.domain.template.purchase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Purchase {

    private PurchaseTemplate template;
    private List<PurchaseItem> items;
}
