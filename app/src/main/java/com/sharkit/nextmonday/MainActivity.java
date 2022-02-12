package com.sharkit.nextmonday;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SEND;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.USERS;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.EMAIL_AND_PASS_FAIL;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.ERROR_AUTHORIZE;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.PASSWORDS_NOT_THE_SAME;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.SUCCESSFUL_UPDATE;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_EMAIL;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_LAST_NAME;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_NAME;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_PASSWORD;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ROLE;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.UserFirebaseEntity.PASSWORD;
import static com.sharkit.nextmonday.configuration.validation.Configuration.hasConnection;
import static com.sharkit.nextmonday.entity.transformer.UserTransformer.transform;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.db.firestore.user.UserFirestore;
import com.sharkit.nextmonday.entity.enums.Role;
import com.sharkit.nextmonday.entity.transformer.UserTransformer;
import com.sharkit.nextmonday.entity.user.FacebookUserDTO;
import com.sharkit.nextmonday.entity.user.GoogleUserDTO;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserPreferenceDTO;

import java.util.Arrays;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView facebook, google;
    private TextView forgotPass;
    private Button createAcc, signIn;
    private LoginButton loginButton;
    private EditText signPass, signEmail, confirmPassword, newPassword;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CallbackManager CM;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 1;
    public static final String TAG = "MainActivity";

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

    @SuppressLint("CommitPrefEdits")
    private void onClickListener() {
        google.setOnClickListener(this);
        createAcc.setOnClickListener(this);
        signIn.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        facebook.setOnClickListener(this);

        if (mAuth.getCurrentUser() != null) {
            setSharedPreference();
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
    }

    public void showForgotPassForm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View root = LayoutInflater.from(this).inflate(R.layout.resetpassword, null);
        findViewOnDialog(root);
        dialog.setPositiveButton(SEND, (dialog1, which) -> {
            if (!ValidationField.isValidField(newPassword, this) || !ValidationField.isValidField(confirmPassword, this)) {
                return;
            }
            if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                Toast.makeText(this, PASSWORDS_NOT_THE_SAME, Toast.LENGTH_SHORT).show();
                return;
            }
            AuthCredential credential = EmailAuthProvider
                    .getCredential(this.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_EMAIL, DEFAULT),
                            this.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_PASSWORD, DEFAULT));

            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                    .reauthenticate(credential)
                    .addOnSuccessListener(unused -> FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .updatePassword(newPassword.getText().toString())
                            .addOnSuccessListener(unused1 -> {
                                FirebaseFirestore.getInstance().collection(USERS)
                                        .document(this.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT))
                                        .update(PASSWORD, newPassword.getText().toString().trim());
                                Toast.makeText(this, SUCCESSFUL_UPDATE, Toast.LENGTH_SHORT).show();
                            }));
        });
        dialog.setView(root);
        dialog.show();
    }

    private void findViewOnDialog(View root) {
        newPassword = root.findViewById(R.id.password_xml);
        confirmPassword = root.findViewById(R.id.confirm_password_xml);
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

                                        FacebookUserDTO facebookUserDTO = FacebookUserDTO.builder()
                                                .email(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())
                                                .id(mAuth.getCurrentUser().getUid())
                                                .name(mAuth.getCurrentUser().getDisplayName())
                                                .role(Role.USER)
                                                .build();

                                        userFirestore.setUser(transform(facebookUserDTO));
                                    }
                                    setSharedPreference();
                                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                                });
                    } else {
                        Toast.makeText(getApplication(), ERROR_AUTHORIZE, Toast.LENGTH_SHORT).show();

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

                                        GoogleUserDTO googleUserDTO = GoogleUserDTO.builder()
                                                .email(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())
                                                .id(mAuth.getCurrentUser().getUid())
                                                .role(Role.USER)
                                                .name(mAuth.getCurrentUser().getDisplayName())
                                                .build();

                                        userFirestore.setUser(transform(googleUserDTO));
                                    }
                                    setSharedPreference();
                                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                                });
                    } else {
                        Toast.makeText(getApplication(), ERROR_AUTHORIZE, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_xml:
                if (hasConnection(getApplicationContext()))
                    signIn();
                break;
            case R.id.create_account_xml:
                startActivity(new Intent(MainActivity.this, RegistrationMenu.class));
                break;
            case R.id.sign_in_xml:
                if (hasConnection(getApplicationContext()))
                    signINWithEmailAndPassword();
                break;
            case R.id.forgot_pass_xml:
                if (hasConnection(getApplicationContext()))
                    showForgotPassForm();
                break;
            case R.id.facebook_xml:
                if (hasConnection(getApplicationContext()))
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
                !ValidationField.isValidField(signPass, getApplicationContext())) {
            return;
        }
        setSharedPreference();
        mAuth.signInWithEmailAndPassword(signEmail.getText().toString(), signPass.getText().toString())
                .addOnSuccessListener(authResult -> startActivity(new Intent(MainActivity.this, MainMenu.class)))
                .addOnFailureListener(e -> Toast.makeText(getApplication(), EMAIL_AND_PASS_FAIL, Toast.LENGTH_SHORT).show());
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

    private void setSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FirebaseFirestore.getInstance().collection(USERS)
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserPreferenceDTO userDTO = documentSnapshot.toObject(UserPreferenceDTO.class);
                    editor.putString(USER_ID, Objects.requireNonNull(userDTO).getId());
                    editor.putString(USER_LAST_NAME, Objects.requireNonNull(userDTO).getLastName());
                    editor.putString(USER_NAME, Objects.requireNonNull(userDTO).getName());
                    editor.putString(USER_EMAIL, Objects.requireNonNull(userDTO).getEmail());
                    editor.putString(USER_PASSWORD, Objects.requireNonNull(userDTO).getPassword());
                    editor.putString(USER_ROLE, Objects.requireNonNull(userDTO).getRole());
                    editor.apply();
                });
    }
}