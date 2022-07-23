package com.sharkit.nextmonday;

import android.content.Context;
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

public class NextMondayActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 15;
    private static final String TAG = NextMondayActivity.class.getCanonicalName();
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.start_activity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                new GoogleRegistration(this).firebaseAuthWithGoogle(task.getResult(ApiException.class).getIdToken());
            } catch (ApiException e) {
                Log.e(TAG, e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static Context getContext() {
        return context;
    }

}