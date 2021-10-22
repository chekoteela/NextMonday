package com.sharkit.nextmonday.db.firestore.diary;

import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.TARGETS;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.TargetFirebaseEntity.ALARM;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.TargetFirebaseEntity.DESCRIPTION;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.TargetFirebaseEntity.STATUS;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.TargetFirebaseEntity.TEXT;

import androidx.navigation.NavController;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.TargetDiary;

public class DiaryFirestore {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void synchronizedDB(TargetDataService service) {
        db.collection(TARGETS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                        service.create(queryDocumentSnapshot.toObject(TargetDiary.class));
                    }
                });
    }

    public void create(TargetDiary targetDiary) {
        db.collection(TARGETS)
                .document(String.valueOf(targetDiary.getTimeForAlarm()))
                .set(targetDiary);
    }

    public void update(TargetDiary targetDiary, long id) {
        db.collection(TARGETS)
                .document(String.valueOf(id))
                .delete();
        db.collection(TARGETS)
                .document(String.valueOf(targetDiary.getTimeForAlarm()))
                .set(targetDiary);
    }

    public void deleteById(long date) {
        db.collection(TARGETS)
                .document(String.valueOf(date))
                .delete();
    }

    public void deleteSimilar(boolean isAlarm, String text, String description, NavController navController) {
        db.collection(TARGETS)
                .whereEqualTo(TEXT, text)
                .whereEqualTo(ALARM, isAlarm)
                .whereEqualTo(DESCRIPTION, description)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                        db.collection(TARGETS)
                                .document(String.valueOf(queryDocumentSnapshot.toObject(TargetDiary.class).getTimeForAlarm()))
                                .delete();
                    }
                    navController.navigate(R.id.nav_diary);
                });
    }
    public void updateStatus(boolean isStatus, long date){
        db.collection(TARGETS)
                .document(String.valueOf(date))
                .update(STATUS, isStatus);
    }
}
