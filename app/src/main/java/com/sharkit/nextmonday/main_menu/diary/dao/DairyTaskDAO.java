package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;

import java.util.List;

@Dao
public interface DairyTaskDAO {

    @Insert(onConflict = 5)
    void create(DiaryTaskDTO diaryTaskDTO);

    @Delete
    void deleteOne(DiaryTaskDTO diaryTaskDTO);

    @Update
    void updateOne(DiaryTaskDTO diaryTaskDTO);

    @Query("UPDATE diary_task SET time_of_alarm = :timeOfAlarm WHERE id = :id")
    void updateTimeOfAlarm(Integer id, Long timeOfAlarm);

    @Query("UPDATE diary_task SET is_completed = :status WHERE diary_task.id = :id")
    void updateStatus(Integer id, Boolean status);

    @Query("UPDATE diary_task SET repeated = 1 WHERE diary_task.id = :id")
    void updateRepeat(Integer id);

    @Query("DELETE FROM diary_task WHERE group_id = :groupId AND is_completed = 0")
    void deleteAllByGroupId(String groupId);

    @Query("UPDATE diary_task SET alarm = :status WHERE id = :id")
    void updateAlarmStatus(Integer id, Boolean status);

    @Query("SELECT * FROM diary_task WHERE diary_task.date = :date")
    List<DiaryTaskDTO> findAllByDate(String date);
}
