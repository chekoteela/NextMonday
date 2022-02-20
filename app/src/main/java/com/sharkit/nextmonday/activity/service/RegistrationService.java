package com.sharkit.nextmonday.activity.service;

import static com.sharkit.nextmonday.activity.transformer.UserTransformer.toUser;
import static com.sharkit.nextmonday.configuration.constant.ButtonText.AGREE_POLICY;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.AGREE_WITH_POLICY;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.USER_WITH_EMAIL_EXIST;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.MainActivity;
import com.sharkit.nextmonday.activity.db.firebase.UserRepository;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

import java.util.Objects;

public class RegistrationService {

    private final Activity activity;
    private final Widget widget;

    public RegistrationService(Activity activity, Widget widget) {
        this.activity = activity;
        this.widget = widget;
    }


    @SuppressLint("InflateParams")
    public void createPolicy() {
        new AlertDialog.Builder(activity)
                .setView(LayoutInflater.from(activity).inflate(R.layout.policy, null))
                .setPositiveButton(AGREE_POLICY(activity), (dialog, which) -> dialog.dismiss())
                .show();
    }

    public void createAccount() {

        if (!ValidationField.isValidName(widget.getTextField().getUserName(), activity)) {
            return;
        }
        if (!ValidationField.isValidName(widget.getTextField().getUserLastName(), activity)) {
            return;
        }
        if (!ValidationField.isValidEmail(widget.getTextField().getEmail(), activity)) {
            return;
        }
        if (!ValidationField.isValidPassword(widget.getTextField().getPassword(), activity)) {
            return;
        }
        if (!widget.getCheckBox().isAgreePolicy().isChecked()) {
            AGREE_WITH_POLICY(activity);
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(widget.getTextField().getEmail().getText().toString(),
                widget.getTextField().getPassword().getText().toString())
                .addOnSuccessListener(authResult -> new UserRepository(activity)
                        .create(toUser(widget.getTextField(),
                                Objects.requireNonNull(mAuth.getCurrentUser()).getUid()))
                        .addOnSuccessListener(unused -> activity.startActivity(new Intent(activity, MainActivity.class))))
                .addOnFailureListener(e -> USER_WITH_EMAIL_EXIST(activity));
    }
}
