package com.sharkit.nextmonday.configuration.navigation;

import static com.sharkit.nextmonday.main_menu.support.configuration.BundleTag.FEEDBACK_ID;
import static com.sharkit.nextmonday.main_menu.support.configuration.BundleTag.LIST_OF_FEEDBACK_MESSAGES;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackMessage;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportNavigation {

    private final Context context;

    public static SupportNavigation getInstance(Context context) {
        return new SupportNavigation(context);
    }

    public void moveToFeedbackMessenger(String feedbackId, ArrayList<FeedbackMessage> messages) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_OF_FEEDBACK_MESSAGES, messages);
        bundle.putString(FEEDBACK_ID, feedbackId);
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment)
                .navigate(R.id.navigation_support_feedback_messenger, bundle);
    }

}
