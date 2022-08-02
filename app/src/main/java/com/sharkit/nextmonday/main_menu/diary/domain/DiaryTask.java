package com.sharkit.nextmonday.main_menu.diary.domain;

import com.sharkit.nextmonday.main_menu.diary.enums.DayOfRepeat;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryTask implements Serializable {

    private Long id;
    private String groupId;
    private String name;
    private String description;
    private String date;
    private List<DayOfRepeat> repeats;
    private Long timeForRepeat;
    private Boolean completed;
    private Boolean repeated;
    private Boolean alarm;
}
