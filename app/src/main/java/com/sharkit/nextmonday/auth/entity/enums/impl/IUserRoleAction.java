package com.sharkit.nextmonday.auth.entity.enums.impl;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.configuration.blocker.TypeBlocker;
import com.sharkit.nextmonday.main_menu.support.entity.enums.MessageType;

public interface IUserRoleAction {

    Task<QuerySnapshot> getAllActiveFeedbacks(final Context context, final String id);

    MessageType getFeedbackMessageType();

    TypeBlocker setBlock();

}
