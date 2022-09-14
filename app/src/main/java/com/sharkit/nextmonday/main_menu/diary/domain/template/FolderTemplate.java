package com.sharkit.nextmonday.main_menu.diary.domain.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderTemplate {

    private Long templateId;
    private String userId;
}
