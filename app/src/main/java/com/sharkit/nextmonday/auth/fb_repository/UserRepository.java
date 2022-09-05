package com.sharkit.nextmonday.auth.fb_repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.NextMondayActivity;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.entity.User;

public class UserRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String path;

    public UserRepository() {
        this.path = NextMondayActivity.getContext().getString(R.string.path_to_user);
    }

    public void create(User user) {
        path = path.replace("{userId}", user.getId());
        db.document(path).set(user);
    }

    public Task<DocumentSnapshot> findById(String id) {
        path = path.replace("{userId}", id);
        return db.document(path)
                .get();
    }
}
