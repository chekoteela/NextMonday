package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;

import java.util.List;

@Dao
public interface DairyTaskDAO {

    @Insert(onConflict = 5)
    void create(DiaryTaskDTO diaryTaskDTO);

    @Query("UPDATE diary_task SET is_completed = :status WHERE diary_task.id = :id")
    void updateStatus(Integer id, Boolean status);

    @Query("UPDATE diary_task SET repeated = 1 WHERE diary_task.id = :id")
    void updateRepeat(Integer id);

    @Query("SELECT * FROM diary_task WHERE diary_task.date = :date")
    List<DiaryTaskDTO> findAllByDate(String date);
}
