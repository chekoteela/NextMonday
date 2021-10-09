package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;

public interface TransformTarget {
    TargetDiary transform(TargetDiaryDTO targetDiaryDTO);
}
