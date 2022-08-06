package com.sharkit.nextmonday.main_menu.diary.transformer;

import com.sharkit.nextmonday.main_menu.diary.domain.template.FolderTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.FolderTemplateDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderTemplateTransformer {

    public static FolderTemplateDTO toFolderTemplateDTO(FolderTemplate template) {
        return FolderTemplateDTO.builder()
                .templateId(template.getTemplateId())
                .build();
    }
}
