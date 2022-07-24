package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;

import java.util.List;

@Dao
public interface DairyTaskDAO {

    @Insert
    void create(DiaryTaskDTO diaryTaskDTO);

    @Query("SELECT * FROM diary_task")
    List<DiaryTaskDTO> findAll();
}
