package com.sharkit.nextmonday.entity.diary;

import lombok.Data;

@Data
public class ParentItemData {
    private String month;
    private int number;
    private String day;
    private int completeTargets;
    private int allTargets;
    private long date;

}
