package com.sharkit.nextmonday.auth.fragment.register;


import static com.sharkit.nextmonday.NextMondayActivity.RC_SIGN_IN;
import static com.sharkit.nextmonday.auth.transformer.UserTransformer.toUser;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.main_menu.NavigationMenu;

import java.util.Objects;

public class GoogleRegistration {

    private final Activity activity;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = GoogleRegistration.class.getCanonicalName();

    public GoogleRegistration(final Activity activity) {
        this.activity = activity;
    }

    private void createRequest() {
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        Log.i(TAG, String.format("create request for google: %s", gso));

        this.mGoogleSignInClient = GoogleSignIn.getClient(this.activity, gso);
    }

    public void signIn() {
        this.createRequest();
        final Intent signInIntent = this.mGoogleSignInClient.getSignInIntent();
        this.activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void firebaseAuthWithGoogle(final String idToken) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        Log.i(TAG, "sign in with google");

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this.activity, task -> {
                    if (Boolean.TRUE.equals(Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser())) {
                        this.createNewUser(mAuth);
                    }
                    this.moveToMainMenu();
                });
    }

    private void moveToMainMenu() {
        this.activity.startActivity(new Intent(this.activity, NavigationMenu.class));
    }

    private void createNewUser(final FirebaseAuth mAuth) {
        final User user = toUser(
                Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                Objects.requireNonNull(mAuth.getCurrentUser().getEmail()),
                Objects.requireNonNull(mAuth.getCurrentUser().getDisplayName()));

        UserRepository.getInstance(this.activity).create(user);
        new UserSharedPreference(this.activity.getApplicationContext()).set(user);
    }
}
