package com.sharkit.nextmonday;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.db.firestore.UserFirestore;
import com.sharkit.nextmonday.entity.enums.Role;
import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;

import java.util.Arrays;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView facebook, google;
    private TextView forgotPass;
    private Button createAcc, signIn, btnEmailReset;
    private LoginButton loginButton;
    private EditText signPass, signEmail, emailReset;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CallbackManager CM;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 1;
    public static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        findView();
        loginButton.setReadPermissions("email");

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        createRequest();
        CM = CallbackManager.Factory.create();
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }
        };
        onClickListener();
    }

    private void onClickListener() {
        google.setOnClickListener(this);
        createAcc.setOnClickListener(this);
        signIn.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        facebook.setOnClickListener(this);

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, MainMenu.class));
        }

        loginButton.registerCallback(CM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                CM = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(CM,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                            }

                            @Override
                            public void onError(FacebookException exception) {
                            }
                        });
            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    private void findView() {
        loginButton = findViewById(R.id.login_button);
        facebook = findViewById(R.id.facebook_xml);
        google = findViewById(R.id.google_xml);
        forgotPass = findViewById(R.id.forgot_pass_xml);
        createAcc = findViewById(R.id.create_account_xml);
        signIn = findViewById(R.id.sign_in_xml);
        signPass = findViewById(R.id.sign_pass_xml);
        signEmail = findViewById(R.id.sign_email_xml);
        btnEmailReset = forgotPass.findViewById(R.id.btnResetPassword);
        emailReset = forgotPass.findViewById(R.id.EmailReset);
    }

    public void showForgotPassForm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View forgotPass = layoutInflater.inflate(R.layout.resetpassword, null);

        btnEmailReset.setOnClickListener(v -> mAuth.sendPasswordResetEmail(emailReset.getText().toString())
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplication(), "Проверте вашу почту", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplication(), "Почта не найдена", Toast.LENGTH_SHORT).show()));
        dialog.setView(forgotPass);
        dialog.show();

    }

    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        UserFirestore userFirestore = new UserFirestore();
                        userFirestore.getDocument(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (!documentSnapshot.exists()) {
                                        FacebookUserDTO facebookUserDTO = new FacebookUserDTO();
                                        facebookUserDTO.setEmail(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
                                        facebookUserDTO.setName(mAuth.getCurrentUser().getDisplayName());
                                        facebookUserDTO.setId(mAuth.getCurrentUser().getUid());
                                        facebookUserDTO.setRole(Role.USER);

                                        userFirestore.setUser(new User().transform(facebookUserDTO));
                                    }
                                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                                });
                    } else {
                        Toast.makeText(getApplication(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        UserFirestore userFirestore = new UserFirestore();
                        userFirestore.getDocument(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (!documentSnapshot.exists()) {

                                        GoogleUserDTO googleUserDTO = new GoogleUserDTO();
                                        googleUserDTO.setEmail(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
                                        googleUserDTO.setName(mAuth.getCurrentUser().getDisplayName());
                                        googleUserDTO.setId(mAuth.getCurrentUser().getUid());
                                        googleUserDTO.setRole(Role.USER);

                                        userFirestore.setUser(new User()
                                                .transform(googleUserDTO));
                                    }
                                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                                });
                    } else {
                        Toast.makeText(getApplication(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_xml:
                signIn();
                break;
            case R.id.create_account_xml:
                startActivity(new Intent(MainActivity.this, RegistrationMenu.class));
                break;
            case R.id.sign_in_xml:
                signINWithEmailAndPassword();
                break;
            case R.id.forgot_pass_xml:
                showForgotPassForm();
                break;
            case R.id.facebook_xml:
                facebookAuthorize();
                break;
        }
    }

    private void facebookAuthorize() {
        CM = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(CM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void signINWithEmailAndPassword() {
        if (!ValidationField.isValidField(signEmail, getApplicationContext()) ||
                !ValidationField.isValidField(signPass, getApplicationContext())){
            return;
        }
        mAuth.signInWithEmailAndPassword(signEmail.getText().toString(), signPass.getText().toString())
                .addOnSuccessListener(authResult -> startActivity(new Intent(MainActivity.this, MainMenu.class)))
                .addOnFailureListener(e -> Toast.makeText(getApplication(), "Вы ввели не верный пароль или почту", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                firebaseAuthWithGoogle(task.getResult(ApiException.class).getIdToken());
            } catch (ApiException e) {
                Log.d(TAG, e.getMessage());
            }
        } else {
            try {
                CM.onActivityResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                Log.d(TAG, e.getMessage());

            }
        }
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}