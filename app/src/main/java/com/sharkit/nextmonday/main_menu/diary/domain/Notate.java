package com.sharkit.nextmonday.main_menu.diary.domain;

import com.sharkit.nextmonday.main_menu.diary.enums.NotateType;
import com.sharkit.nextmonday.main_menu.diary.enums.TemplateType;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notate implements Serializable {

    private Long id;
    private String userId;
    private Long parentFolderId;
    private TemplateType templateType;
    private NotateType notateType;
    private String name;
    private Long templateId;
}
