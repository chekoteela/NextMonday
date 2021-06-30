package com.sharkit.nextmonday.FirebaseEntity;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.Users.MyTarget;

public class TargetEntity {
    String TAG = "qwerty";
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String id = mAuth.getCurrentUser().getUid();
    CollectionReference collectionReference = fs.collection("Users/" + id + "/Targets");

    public void createNewTarget(MyTarget target){
        collectionReference.document(target.getTime_alarm()).set(target);
    }
    public void synchronisedToFirestore(Context context){
     TargetData targetData = new TargetData(context);

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                MyTarget myTarget = queryDocumentSnapshot.toObject(MyTarget.class);
                try {
                    targetData.addNewTarget(myTarget);
                }catch (SQLiteConstraintException e){}
            }
        });
    }
    public void updateTarget(String timeForChange, MyTarget target){
        collectionReference.document(timeForChange).delete();
        collectionReference.document(target.getTime_alarm()).set(target);
    }
}
