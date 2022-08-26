package com.sharkit.nextmonday.main_menu.diary.dao.recipe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeTemplateDTO;

@Dao
public interface RecipeTemplateDAO {

    @Insert
    long create(RecipeTemplateDTO recipeTemplateDTO);

    @Update
    void update(RecipeTemplateDTO recipeTemplateDTO);

    @Query("SELECT * FROM diary_recipe_template WHERE id = :id")
    RecipeDTO findById(Long id);

    @Query("UPDATE diary_recipe_template SET image_cod = :imageCod WHERE id = :id")
    void updateImageCod(String imageCod, Long id);
}
