package com.sharkit.nextmonday.main_menu.diary.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderTemplate {

    private Integer templateId;
    private String name;

}
