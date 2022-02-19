package com.sharkit.nextmonday.activity.service;

import static com.sharkit.nextmonday.activity.transformer.UserTransformer.toUser;
import static com.sharkit.nextmonday.configuration.constant.ButtonText.SEND;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.EMAIL_OR_PASS_FAIL;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.ERROR_AUTHORISE;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.PASSWORDS_NOT_THE_SAME;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.SUCCESSFUL_UPDATE;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.MainMenu;
import com.sharkit.nextmonday.activity.db.firebase.UserRepository;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

import java.util.Objects;

public class Authorisation {

    private GoogleSignInClient mGoogleSignInClient;
    private final Widget widget;
    private final Activity activity;
    private final UserRepository userRepository;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final int RC_SIGN_IN = 1;

    public Authorisation(Activity activity) {
        this.activity = activity;
        userRepository = new UserRepository(activity.getApplicationContext());
        widget = Widget.findByView(activity.getWindow().getDecorView());
    }

    public void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signInWithEmailAndPassword() {
        if (!ValidationField.isValidField(widget.getTextField().getEmail(), activity.getApplicationContext()) ||
                !ValidationField.isValidField(widget.getTextField().getPassword(), activity.getApplicationContext())) {
            return;
        }
//        setSharedPreference();
        mAuth.signInWithEmailAndPassword(widget.getTextField().getEmail().getText().toString(),
                widget.getTextField().getPassword().getText().toString())
                .addOnSuccessListener(authResult -> activity.startActivity(new Intent(activity, MainMenu.class)))
                .addOnFailureListener(e -> EMAIL_OR_PASS_FAIL(activity));
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        userRepository.getUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (!documentSnapshot.exists()) {
                                        userRepository.create(toUser(mAuth.getCurrentUser()));
                                    }
//                                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                                });
                    } else {
                        ERROR_AUTHORISE(activity);
                    }
                });
    }

    public void showForgotPassForm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        View root = LayoutInflater.from(activity).inflate(R.layout.alert_reset_password, null);
        Widget widget = Widget.findByView(root);
        dialog.setPositiveButton(SEND(activity), (dialog1, which) -> {
            if (!ValidationField.isValidField(widget.getTextField().getPassword(), activity) ||
                    !ValidationField.isValidField(widget.getTextField().getConfirmPassword(), activity)) {
                return;
            }
            if (!widget.getTextField().getConfirmPassword().getText().toString()
                    .equals(widget.getTextField().getPassword().getText().toString())) {
                PASSWORDS_NOT_THE_SAME(activity);
                return;
            }
            Objects.requireNonNull(mAuth.getCurrentUser())
                    .updatePassword(widget.getTextField().getConfirmPassword().getText().toString())
                    .addOnSuccessListener(unused -> {
                        userRepository.updatePassword(widget.getTextField().getConfirmPassword().getText().toString(),
                                mAuth.getCurrentUser().getUid());
                        SUCCESSFUL_UPDATE(activity);
                    });
        });
        dialog.setView(root);
        dialog.show();
    }
}
