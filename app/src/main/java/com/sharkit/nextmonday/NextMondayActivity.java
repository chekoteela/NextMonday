package com.sharkit.nextmonday;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sharkit.nextmonday.auth.fragment.register.GoogleRegistration;
import com.sharkit.nextmonday.configuration.exception.GoogleApiException;

public class NextMondayActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 15;
    private static final String TAG = NextMondayActivity.class.getCanonicalName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.start_activity_old);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            final Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                new GoogleRegistration(this).firebaseAuthWithGoogle(task.getResult(ApiException.class).getIdToken());
            } catch (final ApiException e) {
                Log.e(TAG, e.getMessage(), e);
                throw new GoogleApiException(e.getMessage(), e);
            }
        }
    }
}