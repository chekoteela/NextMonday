package com.sharkit.nextmonday.configuration.database;

import static com.sharkit.nextmonday.NextMondayActivity.getContext;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sharkit.nextmonday.main_menu.diary.dao.DairyTaskDAO;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;

@Database(entities = DiaryTaskDTO.class, version = 1, exportSchema = false)
public abstract class NextMondayDatabase extends RoomDatabase {

    public abstract DairyTaskDAO dairyTaskDAO();

    public static NextMondayDatabase getInstance(){
        return Room.databaseBuilder(getContext(), NextMondayDatabase.class, "NextMonday")
                .allowMainThreadQueries()
                .build();
    }
}
