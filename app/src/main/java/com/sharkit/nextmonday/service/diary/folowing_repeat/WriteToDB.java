package com.sharkit.nextmonday.service.diary.folowing_repeat;

import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.TargetDiary;

import java.util.Calendar;
import java.util.Map;

public interface WriteToDB {

    public void writeToDB(TargetDiary targetDiary, Map<String, Boolean> mapRepeats, TargetDataService service, Calendar calendar);
}
