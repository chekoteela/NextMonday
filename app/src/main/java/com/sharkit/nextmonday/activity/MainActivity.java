package com.sharkit.nextmonday.activity;

import static com.sharkit.nextmonday.configuration.validation.Configuration.hasConnection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.service.AuthorisationService;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;
import com.sharkit.nextmonday.configuration.adaptive.service.AdaptiveService;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AuthorisationService authorisationService;
    private Widget widget;
    private static final int RC_SIGN_IN = 1;

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        widget = Widget.findByView(this.getWindow().getDecorView());
        AdaptiveService.setMainActivityAdaptive(TemplateAdaptive.MAIN_ACTIVITY, this, widget);

        authorisationService = new AuthorisationService(this, widget);
        authorisationService.createRequest();
        onClickListener();
    }

    private void onClickListener() {
        widget.getImageView().getGoogle().setOnClickListener(this);
        widget.getButton().getCreateAccount().setOnClickListener(this);
        widget.getButton().getSignIn().setOnClickListener(this);
        widget.getTextView().getForgotPassword().setOnClickListener(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            startActivity(new Intent(MainActivity.this, MainMenu.class));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_xml:
                if (hasConnection(getApplicationContext()))
                    authorisationService.signIn();
                break;
            case R.id.create_account_xml:
                startActivity(new Intent(MainActivity.this, RegistrationMenu.class));
                break;
            case R.id.sign_in_xml:
                if (hasConnection(getApplicationContext()))
                    authorisationService.signInWithEmailAndPassword();
                break;
            case R.id.forgot_password_xml:
                if (hasConnection(getApplicationContext()))
                    authorisationService.showForgotPassForm();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                authorisationService.firebaseAuthWithGoogle(task.getResult(ApiException.class).getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}