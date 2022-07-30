package com.sharkit.nextmonday.configuration.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sharkit.nextmonday.main_menu.diary.dao.DairyTaskDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.FolderTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.NotateTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.FolderTemplateDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateTemplateDTO;

@Database(entities = {DiaryTaskDTO.class, FolderTemplateDTO.class, NotateTemplateDTO.class}, version = 1, exportSchema = false)
public abstract class NextMondayDatabase extends RoomDatabase {

    public abstract DairyTaskDAO dairyTaskDAO();

    public abstract FolderTemplateDAO folderTemplateDAO();

    public abstract NotateTemplateDAO notateTemplateDAO();

    public static NextMondayDatabase getInstance(Context context){
        return Room.databaseBuilder(context, NextMondayDatabase.class, "NextMonday")
                .allowMainThreadQueries()
                .build();
    }

}
