package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;

import java.util.ArrayList;

public class TransformerDiary {

    protected TargetDiary transformer(TargetDiaryDTO targetDiaryDTO){
        TargetDiary targetDiary = new TargetDiary();
        targetDiary.setDate(targetDiaryDTO.getDate());
        targetDiary.setDescription(targetDiaryDTO.getDescription());
        targetDiary.setId(targetDiaryDTO.getId());
        targetDiary.setRepeatFriday(targetDiaryDTO.isRepeatFriday());
        targetDiary.setRepeatMonday(targetDiaryDTO.isRepeatMonday());
        targetDiary.setRepeatSaturday(targetDiaryDTO.isRepeatSaturday());
        targetDiary.setRepeatSunday(targetDiaryDTO.isRepeatSunday());
        targetDiary.setText(targetDiaryDTO.getText());
        targetDiary.setStatus(targetDiaryDTO.isStatus());
        targetDiary.setRepeatTuesday(targetDiaryDTO.isRepeatTuesday());
        targetDiary.setRepeatWednesday(targetDiaryDTO.isRepeatWednesday());
        targetDiary.setRepeatThursday(targetDiaryDTO.isRepeatThursday());
        targetDiary.setTimeForAlarm(targetDiaryDTO.getTimeForAlarm());
        targetDiary.setAlarm(targetDiaryDTO.isAlarm());
        return targetDiary;
    }
    protected ChildItemTargetDTO transformer(TargetDiary targetDiary){
        ChildItemTargetDTO itemTargetDTO = new ChildItemTargetDTO();
        itemTargetDTO.setDate(targetDiary.getTimeForAlarm());
        itemTargetDTO.setDescription(targetDiary.getDescription());
        itemTargetDTO.setStatus(targetDiary.isStatus());
        itemTargetDTO.setText(targetDiary.getText());
        itemTargetDTO.setAlarm(targetDiary.isAlarm());
        return itemTargetDTO;
    }
    protected TargetDiary transformer(TargetDateForAlarmDTO date, TargetDiary targetDiary){
        targetDiary.setRepeatMonday(date.isRepeatMonday());
        targetDiary.setRepeatTuesday(date.isRepeatTuesday());
        targetDiary.setRepeatWednesday(date.isRepeatWednesday());
        targetDiary.setRepeatThursday(date.isRepeatThursday());
        targetDiary.setRepeatFriday(date.isRepeatFriday());
        targetDiary.setRepeatSaturday(date.isRepeatSaturday());
        targetDiary.setRepeatSunday(date.isRepeatSunday());
        return targetDiary;
    }
    protected TargetDateForAlarmDTO transformToAlarmDTO(TargetDiary targetDiary, TargetDateForAlarmDTO date){
        date.setRepeatMonday(targetDiary.isRepeatMonday());
        date.setRepeatTuesday(targetDiary.isRepeatTuesday());
        date.setRepeatWednesday(targetDiary.isRepeatWednesday());
        date.setRepeatThursday(targetDiary.isRepeatThursday());
        date.setRepeatFriday(targetDiary.isRepeatFriday());
        date.setRepeatSaturday(targetDiary.isRepeatSaturday());
        date.setRepeatSunday(targetDiary.isRepeatSunday());
        return date;
    }
    protected ArrayList<Boolean> transformToArray(TargetDateForAlarmDTO alarmDTO){
        ArrayList<Boolean> list = new ArrayList<>();
        list.add(alarmDTO.isRepeatMonday());
        list.add(alarmDTO.isRepeatTuesday());
        list.add(alarmDTO.isRepeatWednesday());
        list.add(alarmDTO.isRepeatThursday());
        list.add(alarmDTO.isRepeatFriday());
        list.add(alarmDTO.isRepeatSaturday());
        list.add(alarmDTO.isRepeatSunday());
        return list;
    }
}
