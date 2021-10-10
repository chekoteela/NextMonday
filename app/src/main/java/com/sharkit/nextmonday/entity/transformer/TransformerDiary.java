package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;
import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserDTO;

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
        return targetDiary;
    }
    protected ChildItemTargetDTO transformer(TargetDiary targetDiary){
        ChildItemTargetDTO itemTargetDTO = new ChildItemTargetDTO();
        itemTargetDTO.setDate(targetDiary.getTimeForAlarm());
        itemTargetDTO.setDescription(targetDiary.getDescription());
        itemTargetDTO.setStatus(targetDiary.isStatus());
        itemTargetDTO.setText(targetDiary.getText());
        return itemTargetDTO;
    }
    protected TargetDiary transformer(TargetDateForAlarmDTO date){
        TargetDiary targetDiary = new TargetDiary();
        targetDiary.setRepeatMonday(date.isRepeatMonday());
        targetDiary.setRepeatTuesday(date.isRepeatTuesday());
        targetDiary.setRepeatWednesday(date.isRepeatWednesday());
        targetDiary.setRepeatThursday(date.isRepeatThursday());
        targetDiary.setRepeatFriday(date.isRepeatFriday());
        targetDiary.setRepeatSaturday(date.isRepeatSaturday());
        targetDiary.setRepeatSunday(date.isRepeatSunday());
        return targetDiary;
    }
}
