package com.sharkit.nextmonday.auth.fragment.register;


import static com.sharkit.nextmonday.NextMondayActivity.RC_SIGN_IN;
import static com.sharkit.nextmonday.auth.fragment.UserTransformer.toUser;

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
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;

import java.util.Objects;

public class GoogleRegistration {

    private final Activity activity;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = GoogleRegistration.class.getCanonicalName();

    public GoogleRegistration(Activity activity1) {
        this.activity = activity1;
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        Log.i(TAG, String.format("create request for google: %s", gso));

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signIn() {
        createRequest();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void firebaseAuthWithGoogle(String idToken) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        Log.i(TAG, "sign in with google");

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                        new UserRepository().create(toUser(
                                Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                                Objects.requireNonNull(mAuth.getCurrentUser().getEmail()),
                                Objects.requireNonNull(mAuth.getCurrentUser().getDisplayName())));
                });
    }
    private void moveToMainMenu(){

    }
}
