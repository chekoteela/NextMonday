package com.sharkit.nextmonday.main_menu.diary.transformer;

import com.sharkit.nextmonday.main_menu.diary.domain.FolderTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.FolderTemplateDTO;

public class FolderTemplateTransformer {

    public static FolderTemplateDTO toFolderTemplateDTO(FolderTemplate template) {
        return FolderTemplateDTO.builder()
                .templateId(template.getTemplateId())
                .build();
    }
}
