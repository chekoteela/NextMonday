package com.sharkit.nextmonday.auth.entity.enums.impl.action;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.auth.entity.enums.impl.IUserRoleAction;
import com.sharkit.nextmonday.main_menu.support.db.FeedbackRepository;
import com.sharkit.nextmonday.main_menu.support.entity.enums.MessageType;

public class UserService implements IUserRoleAction {

    private final FeedbackRepository repository = FeedbackRepository.getInstance();

    @Override
    public Task<QuerySnapshot> getAllActiveFeedbacks(String id) {
        return repository.getAllActiveFeedbacks(id);
    }

    @Override
    public MessageType getFeedbackMessageType() {
        return MessageType.MESSAGE;
    }

}
