package com.sharkit.nextmonday.db.firestore.user;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.configuration.constant.CollectionUser;
import com.sharkit.nextmonday.entity.user.User;

public class UserFirestore extends CollectionUser {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Void> setUser(User user){
        return  db.collection(USERS)
                .document(user.getId())
                .set(user);
    }
    public Task<DocumentSnapshot> getDocument(String id){
        return db.collection(USERS).document(id).get();
    }

    public User getUser(DocumentSnapshot documentSnapshot){
        return documentSnapshot.toObject(User.class);
    }
}
