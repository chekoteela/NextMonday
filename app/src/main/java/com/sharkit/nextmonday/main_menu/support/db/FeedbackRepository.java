package com.sharkit.nextmonday.main_menu.support.db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.NextMondayActivity;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackEntity;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackMessage;
import com.sharkit.nextmonday.main_menu.support.entity.enums.FeedbackStatus;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackRepository {

    private static final String MESSAGES = "messages";
    private static final String FEEDBACK_STATUS = "feedbackStatus";
    private static final String USER_ID = "userId";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String path;

    private static final FeedbackRepository repository = new FeedbackRepository();

    public static FeedbackRepository getInstance() {
        return repository;
    }

    private FeedbackRepository() {
        this.path = NextMondayActivity.getContext().getString(R.string.path_to_feedback);
    }

    public void create(FeedbackEntity entity) {
        db.collection(path).document(entity.getId()).set(entity);
    }

    public Task<QuerySnapshot> getAllActiveFeedbacks(String id) {
        return db.collection(path)
                .whereEqualTo(FEEDBACK_STATUS, FeedbackStatus.ACTIVE)
                .whereEqualTo(USER_ID, id)
                .get();
    }

    public Task<QuerySnapshot> getAllActiveFeedbacks() {
        return db.collection(path)
                .whereEqualTo(FEEDBACK_STATUS, FeedbackStatus.ACTIVE)
                .get();
    }

    public void updateMessage(String feedbackId, List<FeedbackMessage> messages) {
        db.collection(path)
                .document(feedbackId)
                .update(MESSAGES, messages);
    }

    public void closeFeedback(String feedbackId) {
        db.collection(path)
                .document(feedbackId)
                .update(FEEDBACK_STATUS, FeedbackStatus.CLOSED);
    }
}
