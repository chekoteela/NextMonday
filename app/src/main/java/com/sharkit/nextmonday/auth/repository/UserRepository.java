package com.sharkit.nextmonday.auth.repository;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.entity.User;

public class UserRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String path;

    public static UserRepository getInstance(final Context context) {
        return new UserRepository(context);
    }

    private UserRepository(final Context context) {
        this.path = context.getString(R.string.path_to_user);
    }

    public void create(final User user) {
        this.path = this.path.replace("{userId}", user.getId());
        this.db.document(this.path).set(user);
    }

    public Task<DocumentSnapshot> findById(final String id) {
        this.path = this.path.replace("{userId}", id);
        return this.db.document(this.path)
                .get();
    }
}
