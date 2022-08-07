package com.sharkit.nextmonday.main_menu.diary.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sharkit.nextmonday.main_menu.diary.entity.RecipeDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeTemplateDTO;

import java.util.List;

@Dao
public interface RecipeTemplateDAO {

    @Insert
    long create(RecipeTemplateDTO recipeTemplateDTO);

    @Update
    void update(RecipeTemplateDTO recipeTemplateDTO);

    @Query("SELECT * FROM diary_recipe_template")
    List<RecipeDTO> getAll();
}
