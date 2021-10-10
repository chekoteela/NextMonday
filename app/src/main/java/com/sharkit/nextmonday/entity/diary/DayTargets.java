package com.sharkit.nextmonday.entity.diary;

import java.util.ArrayList;

public class DayTargets {
    private ArrayList<ChildItemTargetDTO> targetDTOs;
    private ParentItemData parentItemData;

    public ParentItemData getParentItemData() {
        return parentItemData;
    }

    public void setParentItemData(ParentItemData parentItemData) {
        this.parentItemData = parentItemData;
    }

    public ArrayList<ChildItemTargetDTO> getTargetDTOs() {
        return targetDTOs;
    }

    public void setTargetDTOs(ArrayList<ChildItemTargetDTO> targetDTOs) {
        this.targetDTOs = targetDTOs;
    }

}
