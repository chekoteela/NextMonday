package com.sharkit.nextmonday.activity.db.firebase;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.entity.User;

public class UserRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String path;

    private static final String PASSWORD = "password";

    public UserRepository(Context context) {
        this.path = context.getResources().getString(R.string.path_to_user);
    }

    public void create(User user) {
        db.document(String.format(path, user.getId()))
                .set(user);
    }

    public Task<DocumentSnapshot> getUser(String userId) {
        return db.document(String.format(path, userId))
                .get();
    }

    public void updatePassword(String password, String userId) {
        db.document(String.format(path, userId))
                .update(PASSWORD, password);
    }
}
