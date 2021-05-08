package com.sharkit.nextmonday;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.Users.Users;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.sharkit.nextmonday.MainActivity.isValidEmail;

public class RegistrationMenu extends AppCompatActivity {
     EditText RegistrLastName, RegistrName,RegistrPass,RegistrEmail;
     Button SignIn, CreateAcc;
     FirebaseAuth mAuth;
     FirebaseDatabase db;
     DatabaseReference users;
     GoogleSignInClient mGoogleSignInClient;
     CallbackManager CM;
    static final int RC_SIGN_IN = 123;
    final String TAG = "qwerty";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
            Log.d(TAG,metrics.heightPixels + "");
            int h = metrics.heightPixels;
            setContentView(R.layout.activity_registration_menu);
            ImageView img = findViewById(R.id.imageView);
            RegistrEmail = findViewById(R.id.RegisterEmail);
            RegistrLastName = findViewById(R.id.RegistrLastName);
            RegistrName = findViewById(R.id.RegistrName);
            RegistrPass = findViewById(R.id.RegisterPass);
            SignIn = findViewById(R.id.SignIn);
            CreateAcc =findViewById(R.id.CreateAcc);

            LinearLayout.LayoutParams r_params = new LinearLayout.LayoutParams(h/4,h/4);
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,h/17);
            params.setMargins(h/28,h/85,h/28,h/85);


            img.setLayoutParams(r_params);


            RegistrEmail.setLayoutParams(params);
            RegistrLastName.setLayoutParams(params);
            RegistrName.setLayoutParams(params);
            SignIn.setLayoutParams(params);
            CreateAcc.setLayoutParams(params);
            RegistrPass.setLayoutParams(params);


        getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ImageView googleSign = findViewById(R.id.googleSign);

        CheckBox policy = findViewById(R.id.policy);
        TextView policy_text = findViewById(R.id.policy_text);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");


        policy_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegistrationMenu.this);
                LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
                View forgotPass = layoutInflater.inflate(R.layout.policy, null);
                dialog.setPositiveButton("Я ознакомлен(а)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(forgotPass);
                dialog.show();
            }
        });


        createRequest();
        googleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signIn();
            }
        });

        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(RegistrName.getText().toString())){
                    Toast.makeText(getApplication(),"Введите имя",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(RegistrLastName.getText().toString())){
                    Toast.makeText(getApplication(),"Введите фамилию",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(RegistrEmail.getText().toString())){
                    Toast.makeText(getApplication(),"Введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isValidEmail(RegistrEmail.getText())){
                    Toast.makeText(getApplication(),"Введите коректный формат почты",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(RegistrPass.length() < 5){
                    Toast.makeText(getApplication(),"Введите пароль который больше 5 символов",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!policy.isChecked()){
                    Toast.makeText(getApplication(),"Сначала вы должны согласится с политикой конфиденциальности",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(RegistrEmail.getText().toString(),RegistrPass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Users user = new Users();
                                user.setEmail(RegistrEmail.getText().toString());
                                user.setLastName(RegistrLastName.getText().toString());
                                user.setName(RegistrName.getText().toString());
                                user.setPassword(RegistrPass.getText().toString());
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplication(),"Пользователь зарегистрируван",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegistrationMenu.this,MainActivity.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplication(),"Ошибка добавления пользователя",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(),"Ошибка регистрации",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationMenu.this,MainActivity.class));
                finish();
            }
        });
    }
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
        }else if (requestCode != RC_SIGN_IN) {
            try{
                CM.onActivityResult( requestCode,  resultCode, data);
            }catch (NullPointerException e){}

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
                                            Log.d(TAG, "123456");
                                            Users.setFirebaseUser(false);
                                            Users.setUID(mAuth.getCurrentUser().getUid());
                                            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                            startActivity(intent);
                                        }
                                    } catch (NullPointerException e) {
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        Users user = new Users();
                                        user.setEmail(firebaseUser.getEmail());
                                        user.setName(firebaseUser.getDisplayName());
                                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                        Log.d(TAG, "qwerty");
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
}