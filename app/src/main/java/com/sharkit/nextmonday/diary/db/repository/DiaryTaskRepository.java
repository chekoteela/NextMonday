package com.sharkit.nextmonday.diary.db.repository;

import com.sharkit.nextmonday.configuration.annotation.Query;
import com.sharkit.nextmonday.configuration.annotation.realisation.SQLiteRepository;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

import java.util.List;
import java.util.Optional;

public interface DiaryTaskRepository extends SQLiteRepository<DiaryTask> {

    @Query(condition = "week = '%s' AND date = '%s'")
    Optional<List<DiaryTask>> findAllByWeekAndDate(int week, String date);

    @Query(value = "status = '%s'", condition = "id = '%s'")
    boolean updateStatus(boolean isComplete, String id);
}
