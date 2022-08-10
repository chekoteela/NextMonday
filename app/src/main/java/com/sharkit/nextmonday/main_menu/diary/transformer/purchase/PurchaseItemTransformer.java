package com.sharkit.nextmonday.main_menu.diary.transformer.purchase;

import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseItem;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseItemDTO;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseItemTransformer {

    public static PurchaseItemDTO toPurchaseItemDTO(PurchaseItem item) {
        return PurchaseItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .templateId(item.getTemplateId())
                .status(item.getStatus())
                .build();
    }

    public static PurchaseItem toPurchaseItem(PurchaseItemDTO itemDTO) {
        return PurchaseItem.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .templateId(itemDTO.getTemplateId())
                .status(itemDTO.getStatus())
                .build();
    }

    public static List<PurchaseItem> toPurchaseItems(List<PurchaseItemDTO> itemDTOList) {
        return itemDTOList.stream()
                .map(PurchaseItemTransformer::toPurchaseItem)
                .collect(Collectors.toList());
    }
}
