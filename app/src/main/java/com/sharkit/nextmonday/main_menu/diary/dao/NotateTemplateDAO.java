package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.sharkit.nextmonday.main_menu.diary.entity.NotateTemplateDTO;

@Dao
public interface NotateTemplateDAO {

    @Insert
    long create(NotateTemplateDTO notateTemplateDTO);
}
