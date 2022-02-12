package com.sharkit.nextmonday.entity.diary;

import java.util.ArrayList;

import lombok.Data;

@Data
public class DayTargets {
    private ArrayList<ChildItemTargetDTO> targetDTOs;
    private ParentItemData parentItemData;

}
