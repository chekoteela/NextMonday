package com.sharkit.nextmonday.entity.transformer;

import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;
import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserDTO;

public class Transformer {

    protected User transformer(UserDTO userDTO){
        User user = new User();
        user.setRole(userDTO.getRole());
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        return user;
    }
    protected User transformer(GoogleUserDTO googleUserDTO){
        User user = new User();
        user.setRole(googleUserDTO.getRole());
        user.setId(googleUserDTO.getId());
        user.setName(googleUserDTO.getName());
        user.setEmail(googleUserDTO.getEmail());
        return user;
    }
    protected User transformer(FacebookUserDTO facebookUserDTO){
        User user = new User();
        user.setRole(facebookUserDTO.getRole());
        user.setId(facebookUserDTO.getId());
        user.setName(facebookUserDTO.getName());
        user.setEmail(facebookUserDTO.getEmail());
        return user;
    }
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
}
