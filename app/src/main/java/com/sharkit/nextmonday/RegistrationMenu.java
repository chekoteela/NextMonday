package com.sharkit.nextmonday;

import static com.sharkit.nextmonday.configuration.constant.ToastMessage.AGREE_POLICY;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.AGREE_WITH_POLICY;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.USER_WITH_EMAIL_EXIST;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.configuration.constant.ToastMessage;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.db.firestore.UserFirestore;
import com.sharkit.nextmonday.entity.enums.Role;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserDTO;

import java.util.Objects;

public class RegistrationMenu extends AppCompatActivity implements View.OnClickListener {
    private EditText lastName, name, password, email;
    private TextView policy_text;
    private Button signIn, createAccount;
    private CheckBox policy;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_menu);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        findView();
        onClickListener();
    }

    private void onClickListener() {
        signIn.setOnClickListener(this);
        createAccount.setOnClickListener(this);
        policy_text.setOnClickListener(this);
    }

    private void createPolicy() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegistrationMenu.this);
        LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
        View forgotPass = layoutInflater.inflate(R.layout.policy, null);
        dialog.setPositiveButton(AGREE_POLICY, (dialog1, which) -> dialog1.dismiss());
        dialog.setView(forgotPass);
        dialog.show();
    }

    private void findView() {
        policy = findViewById(R.id.policy);
        email = findViewById(R.id.email_xml);
        lastName = findViewById(R.id.last_name_xml);
        name = findViewById(R.id.name_xml);
        password = findViewById(R.id.password_xml);
        signIn = findViewById(R.id.sign_in_xml);
        createAccount = findViewById(R.id.create_account_xml);
        policy_text = findViewById(R.id.policy_text);
    }

    private void createAccount() {

        if (!ValidationField.isValidName(name, getApplicationContext())){
            return;
        }
        if (!ValidationField.isValidName(lastName, getApplicationContext())) {
            return;
        }
        if (!ValidationField.isValidEmail(email, getApplicationContext())){
            return;
        }
        if (!ValidationField.isValidPassword(password, getApplicationContext())){
            return;
        }
        if (!policy.isChecked()){
            Toast.makeText(getApplicationContext(), AGREE_WITH_POLICY, Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(authResult -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email.getText().toString().trim());
                    userDTO.setId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                    userDTO.setName(name.getText().toString().trim());
                    userDTO.setRole(Role.USER);
                    userDTO.setPassword(password.getText().toString().trim());
                    userDTO.setLastName(lastName.getText().toString().trim());

                    new UserFirestore().setUser(new User().transform(userDTO))
                            .addOnSuccessListener(unused -> startActivity(new Intent(RegistrationMenu.this, MainActivity.class)));
                }).addOnFailureListener(e -> Toast.makeText(getApplication(), USER_WITH_EMAIL_EXIST, Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_xml:
                startActivity(new Intent(RegistrationMenu.this, MainActivity.class));
                break;
            case R.id.create_account_xml:
                if (Configuration.hesConnection(getApplicationContext()))
                createAccount();
                break;
            case R.id.policy_text:
                createPolicy();
                break;
        }
    }
}