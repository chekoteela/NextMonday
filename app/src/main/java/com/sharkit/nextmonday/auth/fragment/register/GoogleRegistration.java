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
                .addOnCompleteListener(this.activity,
                        task -> this.registerUser(mAuth, Boolean.TRUE.equals(Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser())));
    }

    private User buildUser(final FirebaseAuth mAuth) {
        return toUser(
                Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                Objects.requireNonNull(mAuth.getCurrentUser().getEmail()),
                Objects.requireNonNull(mAuth.getCurrentUser().getDisplayName()));
    }

    private void moveToMainMenu() {
        this.activity.startActivity(new Intent(this.activity, NavigationMenu.class));
    }

    private void registerUser(final FirebaseAuth mAuth, final boolean isNewUser) {
        final UserRepository userRepository = UserRepository.getInstance(this.activity);
        if (Boolean.TRUE.equals(isNewUser)) {
            final User user = this.buildUser(mAuth);
            this.setUserToSharedPreferences(user);
            userRepository.create(user);
            this.moveToMainMenu();
        } else {
            userRepository.findById(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                    .addOnSuccessListener(documentSnapshot -> {
                        final User currentUser = documentSnapshot.toObject(User.class);
                        GoogleRegistration.this.setUserToSharedPreferences(currentUser);
                        GoogleRegistration.this.moveToMainMenu();
                    });
        }

    }

    private void setUserToSharedPreferences(final User user) {
        new UserSharedPreference(this.activity.getApplicationContext()).set(user);
    }
}
