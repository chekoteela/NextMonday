package com.sharkit.nextmonday.auth.fb_repository;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.auth.User;

public class UserRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String path;

    public UserRepository(Context context) {
        this.path = context.getString(R.string.path_to_user);
    }

    public void create(User user) {
        path = path.replace("{userId}", user.getId());
        db.document(path).set(user);
    }
}
