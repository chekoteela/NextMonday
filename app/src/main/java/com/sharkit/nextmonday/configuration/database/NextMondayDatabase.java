package com.sharkit.nextmonday.configuration.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sharkit.nextmonday.main_menu.diary.dao.DairyTaskDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.FolderTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.NotateDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.RecipeDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.RecipeItemDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.RecipeTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.FolderTemplateDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeItemDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.RecipeTemplateDTO;

@Database(entities = {RecipeItemDTO.class, DiaryTaskDTO.class, NotateDTO.class, FolderTemplateDTO.class, RecipeTemplateDTO.class}, version = 1, exportSchema = false)
public abstract class NextMondayDatabase extends RoomDatabase {

    public abstract RecipeDAO recipeDAO();

    public abstract RecipeItemDAO recipeItemDAO();

    public abstract DairyTaskDAO dairyTaskDAO();

    public abstract NotateDAO notateDAO();

    public abstract FolderTemplateDAO folderTemplateDAO();

    public abstract RecipeTemplateDAO notateTemplateDAO();

    public static NextMondayDatabase getInstance(Context context){
        return Room.databaseBuilder(context, NextMondayDatabase.class, "NextMonday")
                .allowMainThreadQueries()
                .build();
    }

}
