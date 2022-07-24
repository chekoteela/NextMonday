package com.sharkit.nextmonday.main_menu.diary.domain;

import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryTask {

    private String name;
    private String description;
    private List<DayOfRepeat> repeats;
    private Integer hour;
    private Integer minute;
}
