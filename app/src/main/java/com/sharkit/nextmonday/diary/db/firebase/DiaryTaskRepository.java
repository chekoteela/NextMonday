package com.sharkit.nextmonday.diary.db.firebase;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

public class DiaryTaskRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String path;

    public DiaryTaskRepository(Context context) {
        this.path = context.getString(R.string.path_to_diary_task);
    }

    public Task<Void> create(DiaryTask diaryTask, String date){
        return db.collection(String.format(path, diaryTask.getUserId(), date))
                .document(diaryTask.getId())
                .set(diaryTask);
    }

    public Task<QuerySnapshot> getTaskOnWeek(String userId, String date){
        return db.collection(String.format(path, userId, date))
                .get();
    }
}
