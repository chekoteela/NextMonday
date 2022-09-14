package com.sharkit.nextmonday.main_menu.support.db;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackEntity;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackMessage;
import com.sharkit.nextmonday.main_menu.support.entity.enums.FeedbackStatus;

import java.util.List;

public class FeedbackRepository {

    private static final String MESSAGES = "messages";
    private static final String FEEDBACK_STATUS = "feedbackStatus";
    private static final String USER_ID = "userId";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String path;

    public static FeedbackRepository getInstance(final Context context) {
        return new FeedbackRepository(context);
    }

    private FeedbackRepository(final Context context) {
        this.path = context.getString(R.string.path_to_feedback);
    }

    public void create(final FeedbackEntity entity) {
        this.db.collection(this.path).document(entity.getId()).set(entity);
    }

    public Task<QuerySnapshot> getAllActiveFeedbacks(final String id) {
        return this.db.collection(this.path)
                .whereEqualTo(FEEDBACK_STATUS, FeedbackStatus.ACTIVE)
                .whereEqualTo(USER_ID, id)
                .get();
    }

    public Task<QuerySnapshot> getAllActiveFeedbacks() {
        return this.db.collection(this.path)
                .whereEqualTo(FEEDBACK_STATUS, FeedbackStatus.ACTIVE)
                .get();
    }

    public void updateMessage(final String feedbackId, final List<FeedbackMessage> messages) {
        this.db.collection(this.path)
                .document(feedbackId)
                .update(MESSAGES, messages);
    }

    public void closeFeedback(final String feedbackId) {
        this.db.collection(this.path)
                .document(feedbackId)
                .update(FEEDBACK_STATUS, FeedbackStatus.CLOSED);
    }
}
