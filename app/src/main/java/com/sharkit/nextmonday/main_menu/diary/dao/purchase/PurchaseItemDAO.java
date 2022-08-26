package com.sharkit.nextmonday.main_menu.diary.dao.purchase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseItemDTO;

@Dao
public interface PurchaseItemDAO {

    @Insert
    void create(PurchaseItemDTO itemDTO);

    @Update
    void update(PurchaseItemDTO itemDTO);

    @Delete
    void delete(PurchaseItemDTO itemDTO);

    @Query("UPDATE purchase_item SET status = :isComplete WHERE id = :id")
    void updateStatus(Boolean isComplete, Long id);
}
