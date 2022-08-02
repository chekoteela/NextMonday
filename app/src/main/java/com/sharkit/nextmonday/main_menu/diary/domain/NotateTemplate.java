package com.sharkit.nextmonday.main_menu.diary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotateTemplate {

    private Long templateId;
    private String date;
    private String text;
}
