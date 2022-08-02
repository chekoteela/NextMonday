package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.sharkit.nextmonday.main_menu.diary.entity.FolderTemplateDTO;

@Dao
public interface FolderTemplateDAO {

    @Insert
    long create(FolderTemplateDTO folderTemplateDTO);
}
