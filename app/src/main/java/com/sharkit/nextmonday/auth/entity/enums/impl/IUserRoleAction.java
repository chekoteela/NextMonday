package com.sharkit.nextmonday.auth.entity.enums.impl;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.main_menu.support.entity.enums.MessageType;

public interface IUserRoleAction {

    Task<QuerySnapshot> getAllActiveFeedbacks(String id);

    MessageType getFeedbackMessageType();
}
