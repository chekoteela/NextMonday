package com.sharkit.nextmonday.auth.entity.enums;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.auth.entity.enums.impl.IUserRoleAction;
import com.sharkit.nextmonday.auth.entity.enums.impl.action.AdminService;
import com.sharkit.nextmonday.auth.entity.enums.impl.action.UserService;
import com.sharkit.nextmonday.main_menu.support.entity.enums.MessageType;

public enum UserRole implements IUserRoleAction {
    ADMIN,
    USER;

    @Override
    public Task<QuerySnapshot> getAllActiveFeedbacks(Context context, String id) {
        return getService().getAllActiveFeedbacks(context, id);
    }

    @Override
    public MessageType getFeedbackMessageType() {
        return getService().getFeedbackMessageType();
    }

    private IUserRoleAction getService() {
        switch (this) {
            case ADMIN:
                return new AdminService();
            case USER:
            default: return new UserService();
        }
    }
}
