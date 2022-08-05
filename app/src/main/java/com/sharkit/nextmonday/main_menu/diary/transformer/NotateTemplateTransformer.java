package com.sharkit.nextmonday.main_menu.diary.transformer;

import com.sharkit.nextmonday.main_menu.diary.domain.NotateTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateTemplateDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotateTemplateTransformer {

    public static NotateTemplateDTO toNotateTemplateDTO(NotateTemplate notateTemplate) {
        return NotateTemplateDTO.builder()
                .date(notateTemplate.getDate())
                .text(notateTemplate.getText())
                .templateId(notateTemplate.getTemplateId())
                .build();
    }
}
