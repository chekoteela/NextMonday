package com.sharkit.nextmonday.configuration.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sharkit.nextmonday.NextMondayActivity;
import com.sharkit.nextmonday.main_menu.diary.dao.DairyTaskDAO;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;

import java.util.Optional;

@Database(entities = DiaryTaskDTO.class, version = 1, exportSchema = false)
public abstract class NextMondayDatabase extends RoomDatabase {


    public abstract DairyTaskDAO dairyTaskDAO();

    public static NextMondayDatabase getInstance(Context context){
        return Room.databaseBuilder(context, NextMondayDatabase.class, "NextMonday")
                .allowMainThreadQueries()
                .build();
    }

}
