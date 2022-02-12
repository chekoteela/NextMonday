package com.sharkit.nextmonday.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sharkit.nextmonday.R;

public class RegistrationMenu extends AppCompatActivity {
    private EditText lastName, name, password, email;
    private TextView policy_text;
    private Button signIn, createAccount;
    private CheckBox policy;


    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_menu);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        findView();
//        onClickListener();
    }
//
//    private void onClickListener() {
//        signIn.setOnClickListener(this);
//        createAccount.setOnClickListener(this);
//        policy_text.setOnClickListener(this);
//    }
//
//    private void createPolicy() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(RegistrationMenu.this);
//        LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
//        View forgotPass = layoutInflater.inflate(R.layout.policy, null);
//        dialog.setPositiveButton(AGREE_POLICY, (dialog1, which) -> dialog1.dismiss());
//        dialog.setView(forgotPass);
//        dialog.show();
//    }
//
//    private void findView() {
//        policy = findViewById(R.id.policy);
//        email = findViewById(R.id.email_xml);
//        lastName = findViewById(R.id.last_name_xml);
//        name = findViewById(R.id.name_xml);
//        password = findViewById(R.id.password_xml);
//        signIn = findViewById(R.id.sign_in_xml);
//        createAccount = findViewById(R.id.create_account_xml);
//        policy_text = findViewById(R.id.policy_text);
//    }
//
//    private void createAccount() {
//
//        if (!ValidationField.isValidName(name, getApplicationContext())) {
//            return;
//        }
//        if (!ValidationField.isValidName(lastName, getApplicationContext())) {
//            return;
//        }
//        if (!ValidationField.isValidEmail(email, getApplicationContext())) {
//            return;
//        }
//        if (!ValidationField.isValidPassword(password, getApplicationContext())) {
//            return;
//        }
//        if (!policy.isChecked()) {
//            Toast.makeText(getApplicationContext(), AGREE_WITH_POLICY, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
//                .addOnSuccessListener(authResult -> {
//                    User user = User.builder()
//                            .id(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
//                            .email(email.getText().toString().trim())
//                            .name(name.getText().toString().trim())
//                            .lastName(lastName.getText().toString().trim())
//                            .password(password.getText().toString().trim())
//                            .role(Role.USER)
//                            .build();
//
//                    new UserFirestore().setUser(user)
//                            .addOnSuccessListener(unused -> startActivity(new Intent(RegistrationMenu.this, MainActivity.class)));
//                }).addOnFailureListener(e -> Toast.makeText(getApplication(), USER_WITH_EMAIL_EXIST, Toast.LENGTH_SHORT).show());
//    }
//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_xml:
//                startActivity(new Intent(RegistrationMenu.this, MainActivity.class));
//                break;
//            case R.id.create_account_xml:
//                if (hasConnection(getApplicationContext()))
//                    createAccount();
//                break;
//            case R.id.policy_text:
//                createPolicy();
//                break;
//        }
//    }
}