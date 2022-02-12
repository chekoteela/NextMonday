package com.sharkit.nextmonday.entity.diary;

import com.sharkit.nextmonday.entity.transformer.TransformerDiary;

import java.util.ArrayList;

import lombok.Data;

@Data
public class TargetDateForAlarmDTO extends TransformerDiary {
    private boolean repeatMonday;
    private boolean repeatTuesday;
    private boolean repeatWednesday;
    private boolean repeatThursday;
    private boolean repeatFriday;
    private boolean repeatSaturday;
    private boolean repeatSunday;

    public TargetDiary transform (TargetDiary targetDiary){
        return transformer(this, targetDiary);
    }
    public ArrayList<Boolean> toArray(){
       return transformToArray(this);
    }
}
