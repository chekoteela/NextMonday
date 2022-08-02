package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateDTO;

@Dao
public interface NotateDAO {

    @Insert
    long create(NotateDTO dto);
}
