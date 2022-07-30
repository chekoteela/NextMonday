package com.sharkit.nextmonday.main_menu.diary.domain;

import com.sharkit.nextmonday.main_menu.diary.enums.TemplateType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notate {

    private Integer id;
    private TemplateType type;
    private Integer templateId;
}
