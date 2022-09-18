package com.sharkit.nextmonday.main_menu.support.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.exception.UnexpectedPositionException;
import com.sharkit.nextmonday.configuration.navigation.SupportNavigation;
import com.sharkit.nextmonday.main_menu.support.db.FeedbackRepository;
import com.sharkit.nextmonday.main_menu.support.dialog.widget.SupportDialogWidget;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackEntity;
import com.sharkit.nextmonday.main_menu.support.entity.enums.FeedbackStatus;
import com.sharkit.nextmonday.main_menu.support.entity.enums.FeedbackType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateFeedbackDialog {

    private final Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this.context).create();
        final View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_support_create_feedback, null);
        final SupportNavigation navigation = SupportNavigation.getInstance(this.context);
        final SupportDialogWidget.NewFeedbackDialogWidget widget = SupportDialogWidget.newInstance(view).getNewFeedbackDialogWidget();

        widget.getCreate().setOnClickListener(v -> {
            final FeedbackEntity entity = FeedbackEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .feedbackStatus(FeedbackStatus.ACTIVE)
                    .feedbackType(this.getFeedbackType(widget.getTypeOfFeedback().getSelectedItemPosition()))
                    .messages(new ArrayList<>())
                    .tittle(widget.getTittle().getText().toString())
                    .userId(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .build();

            dialog.dismiss();

            FeedbackRepository.getInstance(this.context).create(entity);
            navigation.moveToFeedbackMessenger(entity.getId(), entity.getMessages());
        });

        dialog.setView(view);
        dialog.show();
    }

    private FeedbackType getFeedbackType(final int position) {
        switch (position) {
            case 0:
                return FeedbackType.ERROR;
            case 1:
                return FeedbackType.WISH;
            case 2:
                return FeedbackType.QUESTION;
            default: throw new UnexpectedPositionException(position);
        }
    }
}
