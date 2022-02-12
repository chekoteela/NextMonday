package com.sharkit.nextmonday.activity.service;

import static com.sharkit.nextmonday.activity.transformer.UserTransformer.toUser;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.EMAIL_AND_PASS_FAIL;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.ERROR_AUTHORIZE;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.MainMenu;
import com.sharkit.nextmonday.activity.db.firebase.FirebaseUser;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

import java.util.Objects;

public class Authorisation {

    private GoogleSignInClient mGoogleSignInClient;
    private final Widget widget;
    private final Activity activity;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final int RC_SIGN_IN = 1;

    public Authorisation(Activity activity){
        this.activity = activity;
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
                .addOnFailureListener(e -> Toast.makeText(activity.getApplicationContext(), EMAIL_AND_PASS_FAIL, Toast.LENGTH_SHORT).show());
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser userFirestore = new FirebaseUser(activity.getApplicationContext());
                        userFirestore.getUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (!documentSnapshot.exists()) {

                                        userFirestore.create(toUser(mAuth.getCurrentUser().getUid(),
                                                mAuth.getCurrentUser().getEmail(),
                                                mAuth.getCurrentUser().getDisplayName()
                                        ));
                                    }
//                                    setSharedPreference();
//                                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                                });
                    } else {
                        Toast.makeText(activity.getApplicationContext(), ERROR_AUTHORIZE, Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void setSharedPreference() {
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        FirebaseFirestore.getInstance().collection(USERS)
//                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    UserPreferenceDTO userDTO = documentSnapshot.toObject(UserPreferenceDTO.class);
//                    editor.putString(USER_ID, Objects.requireNonNull(userDTO).getId());
//                    editor.putString(USER_LAST_NAME, Objects.requireNonNull(userDTO).getLastName());
//                    editor.putString(USER_NAME, Objects.requireNonNull(userDTO).getName());
//                    editor.putString(USER_EMAIL, Objects.requireNonNull(userDTO).getEmail());
//                    editor.putString(USER_PASSWORD, Objects.requireNonNull(userDTO).getPassword());
//                    editor.putString(USER_ROLE, Objects.requireNonNull(userDTO).getRole());
//                    editor.apply();
//                });
//    }
//
//    public void showForgotPassForm() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        View root = LayoutInflater.from(this).inflate(R.layout.resetpassword, null);
//        findViewOnDialog(root);
//        dialog.setPositiveButton(SEND, (dialog1, which) -> {
//            if (!ValidationField.isValidField(newPassword, this) || !ValidationField.isValidField(confirmPassword, this)) {
//                return;
//            }
//            if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
//                Toast.makeText(this, PASSWORDS_NOT_THE_SAME, Toast.LENGTH_SHORT).show();
//                return;
//            }
//            AuthCredential credential = EmailAuthProvider
//                    .getCredential(this.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_EMAIL, DEFAULT),
//                            this.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_PASSWORD, DEFAULT));
//
//            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
//                    .reauthenticate(credential)
//                    .addOnSuccessListener(unused -> FirebaseAuth.getInstance()
//                            .getCurrentUser()
//                            .updatePassword(newPassword.getText().toString())
//                            .addOnSuccessListener(unused1 -> {
//                                FirebaseFirestore.getInstance().collection(USERS)
//                                        .document(this.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT))
//                                        .update(PASSWORD, newPassword.getText().toString().trim());
//                                Toast.makeText(this, SUCCESSFUL_UPDATE, Toast.LENGTH_SHORT).show();
//                            }));
//        });
//        dialog.setView(root);
//        dialog.show();
//    }
}
