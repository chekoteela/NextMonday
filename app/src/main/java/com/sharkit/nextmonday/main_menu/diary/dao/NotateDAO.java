package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sharkit.nextmonday.main_menu.diary.entity.NotateDTO;

import java.util.List;

@Dao
public interface NotateDAO {

    @Insert
    long create(NotateDTO dto);

    @Query("SELECT * FROM diary_notate WHERE parent_folder_id = :parentFolderId")
    List<NotateDTO> getAllNotateByParentFolderId(Long parentFolderId);
}
