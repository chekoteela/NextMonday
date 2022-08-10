package com.sharkit.nextmonday.main_menu.diary.dao.purchase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseDTO;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseTemplateDTO;

@Dao
public interface PurchaseTemplateDAO {

    @Insert
    Long create(PurchaseTemplateDTO templateDTO);

    @Update
    void update(PurchaseTemplateDTO templateDTO);

    @Query("SELECT * FROM diary_purchase_template WHERE id = :id")
    PurchaseDTO findById(Long id);
}
