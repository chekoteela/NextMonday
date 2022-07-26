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

    private Integer id;
    private String name;
    private String description;
    private String date;
    private List<DayOfRepeat> repeats;
    private Long timeForRepeat;
    private boolean completed;
    private boolean repeated;
}
