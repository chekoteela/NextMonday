package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.sharkit.nextmonday.main_menu.diary.entity.RecipeItemDTO;

@Dao
public interface RecipeItemDAO {

    @Insert
    void save(RecipeItemDTO itemDTO);
}
