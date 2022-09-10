package com.sharkit.nextmonday.configuration.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sharkit.nextmonday.main_menu.diary.dao.DairyTaskDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.FolderTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.NotateDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.purchase.PurchaseItemDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.purchase.PurchaseTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.recipe.RecipeItemDAO;
import com.sharkit.nextmonday.main_menu.diary.dao.recipe.RecipeTemplateDAO;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.FolderTemplateDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseItemDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseTemplateDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeItemDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.recipe.RecipeTemplateDTO;

import static com.sharkit.nextmonday.configuration.database.NextMondayMigration.MIGRATION_1_2;

@Database(entities = {PurchaseTemplateDTO.class, PurchaseItemDTO.class, RecipeItemDTO.class,
        DiaryTaskDTO.class, NotateDTO.class, FolderTemplateDTO.class,
        RecipeTemplateDTO.class}, version = 2)
public abstract class NextMondayDatabase extends RoomDatabase {

    public abstract RecipeItemDAO recipeItemDAO();

    public abstract DairyTaskDAO dairyTaskDAO();

    public abstract NotateDAO notateDAO();

    public abstract FolderTemplateDAO folderTemplateDAO();

    public abstract RecipeTemplateDAO recipeTemplateDAO();

    public abstract PurchaseItemDAO purchaseItemDAO();

    public abstract PurchaseTemplateDAO purchaseTemplateDAO();

    public static NextMondayDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, NextMondayDatabase.class, "NextMonday")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build();
    }

}
