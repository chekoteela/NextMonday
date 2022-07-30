package com.sharkit.nextmonday.main_menu.diary.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotateTemplate {

    private Integer templateId;
    private String date;
    private String text;
    private String name;
}
