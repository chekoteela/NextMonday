package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.sharkit.nextmonday.main_menu.diary.entity.RecipeDTO;

@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM diary_recipe_template WHERE id = :id")
    RecipeDTO findById(Long id);
}
