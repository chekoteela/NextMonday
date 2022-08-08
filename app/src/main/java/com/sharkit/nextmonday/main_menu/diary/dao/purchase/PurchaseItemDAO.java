package com.sharkit.nextmonday.main_menu.diary.dao.purchase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseItemDTO;

@Dao
public interface PurchaseItemDAO {

    @Insert
    void create(PurchaseItemDTO itemDTO);

    @Update
    void update(PurchaseItemDTO itemDTO);
}
