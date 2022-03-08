package com.sharkit.nextmonday.diary.db.sqlite;

import android.content.Context;

import com.sharkit.nextmonday.configuration.annotation.realisation.SQLiteTemplate;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

public class DiaryTaskRepo extends SQLiteTemplate<DiaryTask> {

    public DiaryTaskRepo(Context context) {
        super(context, DiaryTask.class);
    }

}
