package com.sharkit.nextmonday.main_menu.diary.transformer.purchase;

import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseItemTransformer.toPurchaseItems;
import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseTemplateTransformer.toPurchaseTemplate;

import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.Purchase;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseTransformer {

    public static Purchase toPurchase(final PurchaseDTO dto) {
        return Purchase.builder()
                .template(toPurchaseTemplate(dto.getTemplate()))
                .items(toPurchaseItems(dto.getItems()))
                .build();
    }
}
