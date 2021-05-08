package com.sharkit.nextmonday;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.facebook.FacebookSdk;

import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Users;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    ImageView facebook, google;
    TextView forgotPass;
    Button CreateAcc, SignIn, btnEmailReset;
    EditText SignPass, SignEmail, EmailReset;

    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference users;
    CallbackManager CM;
    GoogleSignInClient mGoogleSignInClient;
    AccessTokenTracker accessTokenTracker;

    static final int RC_SIGN_IN = 123;
    final String TAG = "qwerty";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;

        getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView img = findViewById(R.id.imageView2);
        facebook = findViewById(R.id.fb);
        google = findViewById(R.id.google);
        forgotPass = findViewById(R.id.forgotPass);
        CreateAcc = findViewById(R.id.CreateAcc);
        SignIn = findViewById(R.id.SignIn);
        SignPass = findViewById(R.id.SignPass);
        SignEmail = findViewById(R.id.SignEmail);

        LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(h / 4, h / 4);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, h / 17);
        params.setMargins(h / 28, h / 85, h / 28, h / 85);


        img.setLayoutParams(r_params);

        CreateAcc.setLayoutParams(params);
        SignEmail.setLayoutParams(params);
        SignIn.setLayoutParams(params);
        SignPass.setLayoutParams(params);
        forgotPass.setLayoutParams(params);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        createRequest();

        Log.d(TAG, mAuth.getCurrentUser().getUid());
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Target.setModerator("1");
            Users.setUID(mAuth.getCurrentUser().getUid());
            startActivity(new Intent(MainActivity.this, MainMenu.class));
            finish();
        }

        CM = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // Callback registration
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
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPassForm();
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(SignEmail.getText())) {
                    Toast.makeText(getApplication(), "Введите коректный формат почты", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (TextUtils.isEmpty(SignEmail.getText().toString())) {
                    Toast.makeText(getApplication(), "Введите почту", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (TextUtils.isEmpty(SignPass.getText().toString())) {
                    Toast.makeText(getApplication(), "Введите пароль", Toast.LENGTH_SHORT).show();

                    return;
                }
                mAuth.signInWithEmailAndPassword(SignEmail.getText().toString(), SignPass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Users.setFirebaseUser(true);
                                startActivity(new Intent(MainActivity.this, MainMenu.class));
                                Users.setUID(mAuth.getCurrentUser().getUid());
                                finish();
                                recreate();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(), "Вы ввели не верный пароль или почту", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationMenu.class));

            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
//        facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CM = CallbackManager.Factory.create();
//                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email","public_profile"));
//                LoginManager.getInstance().registerCallback(CM, new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//                        Log.d(TAG,"OnSuccess");
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.d(TAG,"OnCancel");
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Log.d(TAG,"OnError");
//                    }
//                });
//            }
//        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }
        };
    }

    public void showForgotPassForm() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View forgotPass = layoutInflater.inflate(R.layout.resetpassword, null);
        btnEmailReset = forgotPass.findViewById(R.id.btnResetPassword);
        EmailReset = forgotPass.findViewById(R.id.EmailReset);
        btnEmailReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(EmailReset.getText().toString())) {
                    Toast.makeText(getApplication(), "Введите почту", Toast.LENGTH_SHORT).show();

                    return;
                }
                mAuth.sendPasswordResetEmail(EmailReset.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(), "Проверте вашу почту", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(), "Почта не найдена", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        dialog.setView(forgotPass);
        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Users.setFirebaseUser(false);
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser == null) {
                                Users us = new Users();
                                us.setEmail(firebaseUser.getEmail());
                                us.setName(firebaseUser.getDisplayName());


                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(us);
                                startActivity(new Intent(MainActivity.this, MainMenu.class));
                                finish();
                                recreate();
                            } else {
                                startActivity(new Intent(MainActivity.this, MainMenu.class));
                                finish();
                                recreate();
                            }
                        } else {
                            Toast.makeText(getApplication(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();

                        }
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

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
            }
        } else if (requestCode != RC_SIGN_IN) {
            try {
                CM.onActivityResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
            }

        }
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                                users.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Users us = snapshot.getValue(Users.class);


                                    try {

                                        if (null != us.getEmail()) {

                                            Users.setFirebaseUser(false);
                                            Users.setUID(mAuth.getCurrentUser().getUid());
                                            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                            startActivity(intent);
                                        }
                                    }catch (NullPointerException e){
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        Users user = new Users();
                                        user.setEmail(firebaseUser.getEmail());
                                        user.setName(firebaseUser.getDisplayName());

                                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                        Users.setFirebaseUser(false);
                                        Users.setUID(mAuth.getCurrentUser().getUid());
                                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                        startActivity(intent);
                                    }
                                        finish();
                                        recreate();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                        } else {
                            Toast.makeText(getApplication(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}