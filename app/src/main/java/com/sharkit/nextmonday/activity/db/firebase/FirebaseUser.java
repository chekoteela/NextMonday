package com.sharkit.nextmonday.activity.db.firebase;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.entity.User;

public class FirebaseUser {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Resources resource;

    public FirebaseUser(Context context){
        this.resource = context.getResources();
    }

    public void create (User user){
        db.document(String.format(resource.getString(R.string.path_to_user), user.getId()))
                .set(user);
    }

    public Task<DocumentSnapshot> getUser(String id){
        return db.document(String.format(resource.getString(R.string.path_to_user), id))
                .get();
    }
}
