package com.sharkit.nextmonday.entity.diary;

import com.sharkit.nextmonday.entity.transformer.TransformerDiary;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChildItemTargetDTO extends TransformerDiary implements Serializable {
    private String text;
    private String description;
    private long date;
    private boolean status;
    private boolean alarm;

    public ChildItemTargetDTO transform (TargetDiary targetDiary){
        return transformer(targetDiary);
    }
}
