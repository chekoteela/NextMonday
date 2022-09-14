package com.sharkit.nextmonday.main_menu.diary.transformer.purchase;

import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseTemplateDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseTemplateTransformer {

    public static PurchaseTemplateDTO toPurchaseTemplateDTO(final PurchaseTemplate template) {
        return PurchaseTemplateDTO.builder()
                .date(template.getDate())
                .id(template.getId())
                .text(template.getText())
                .build();
    }

    public static PurchaseTemplate toPurchaseTemplate(final PurchaseTemplateDTO templateDTO) {
        return PurchaseTemplate.builder()
                .date(templateDTO.getDate())
                .id(templateDTO.getId())
                .text(templateDTO.getText())
                .build();
    }

}
