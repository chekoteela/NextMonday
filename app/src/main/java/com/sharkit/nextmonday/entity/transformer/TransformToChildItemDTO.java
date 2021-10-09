package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;

public interface TransformToChildItemDTO {
    ChildItemTargetDTO transform(TargetDiary targetDiary);
}
