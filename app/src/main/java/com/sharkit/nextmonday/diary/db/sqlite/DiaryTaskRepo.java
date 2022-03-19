package com.sharkit.nextmonday.diary.db.sqlite;

import android.content.Context;

import com.sharkit.nextmonday.configuration.annotation.realisation.SQLiteTemplate;
import com.sharkit.nextmonday.diary.db.repository.DiaryTaskRepository;
import com.sharkit.nextmonday.diary.entity.DiaryTask;
import com.sharkit.nextmonday.trash.PRepo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DiaryTaskRepo extends SQLiteTemplate<DiaryTask> implements DiaryTaskRepository {

    private DiaryTaskRepo(Context context) {
        super(context, DiaryTask.class);
        onCreate(super.getReadableDatabase());
    }

    public static DiaryTaskRepo getInstance(Context context){
        return new DiaryTaskRepo(context);
    }

    @Override
    public Optional<List<DiaryTask>> findAllByWeekAndDate(int week, String date) {
        return find(DiaryTaskRepository.class, Objects.requireNonNull(new Object() {}
                .getClass().getEnclosingMethod()).getName(), week, date);    }

    @Override
    public boolean updateStatus(boolean isComplete, String id) {
        return update(DiaryTaskRepository.class, Objects.requireNonNull(new Object() {}
                .getClass().getEnclosingMethod()).getName(), isComplete, id)
                .orElse(false);
    }

    @Override
    public boolean updateRewriteRepeater(boolean isRewriteRepeater, String id) {
        return update(DiaryTaskRepository.class, Objects.requireNonNull(new Object() {}
                .getClass().getEnclosingMethod()).getName(), isRewriteRepeater, id)
                .orElse(false);
    }
}
