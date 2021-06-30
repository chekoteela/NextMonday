package com.sharkit.nextmonday.FirebaseEntity;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.Users.MyTarget;

import java.util.Objects;

public class TargetEntity {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    CollectionReference collectionReference = fs.collection("Users/" + id + "/Targets");

    public void createNewTarget(MyTarget target){
        collectionReference.document(String.valueOf(target.getTime_alarm())).set(target);
    }
    public void synchronisedToFirestore(Context context){
     TargetData targetData = new TargetData(context);

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                MyTarget myTarget = queryDocumentSnapshot.toObject(MyTarget.class);
                try {
                    targetData.addNewTarget(myTarget);
                }catch (SQLiteConstraintException ignored){}
            }
        });
    }
    public void updateTarget(Long timeForChange, MyTarget target){
        collectionReference.document(String.valueOf(timeForChange)).delete();
        collectionReference.document(String.valueOf(target.getTime_alarm())).set(target);
    }

    public void deleteTarget(Long time){
        collectionReference.document(String.valueOf(time)).delete();
    }
    public void completeTarget(Long key, boolean isChecked){
        collectionReference.document(String.valueOf(key)).update("status", isChecked);
    }
}
