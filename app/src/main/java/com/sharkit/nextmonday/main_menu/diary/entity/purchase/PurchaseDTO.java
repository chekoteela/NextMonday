package com.sharkit.nextmonday.main_menu.diary.entity.purchase;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseItem;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseTemplate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PurchaseDTO {
    @Embedded
    private PurchaseTemplateDTO template;

    @Relation(parentColumn = "id",
            entityColumn = "template_id")
    private List<PurchaseItemDTO> items;
}
