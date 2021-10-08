package com.sharkit.nextmonday;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.sharkit.nextmonday.db.firestore.UserFirestore;
import com.sharkit.nextmonday.entity.enums.Role;
import com.sharkit.nextmonday.entity.user.User;
import com.sharkit.nextmonday.entity.user.UserDTO;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationMenu extends AppCompatActivity implements View.OnClickListener {
    private EditText lastName, name, password, email;
    private TextView policy_text;
    private Button signIn, createAccount;
    private CheckBox policy;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        dialog.setPositiveButton("Я ознакомлен(а)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
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


    public boolean Validator(String s) {
        Pattern num = Pattern.compile("[0-9]");
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,`]°•○●□■♤♡◇♧☆▪¤《》¡¿,.]");
        Pattern space = Pattern.compile(" ");
        Matcher hasSpace = space.matcher(s);
        Matcher hasNum = num.matcher(s);
        Matcher hasSigns = sign.matcher(s);

        return (hasSigns.find() || hasNum.find() || hasSpace.find());
    }

    public boolean ValidatorPass(String s) {
        Pattern cyrillic = Pattern.compile("[а-яА-Я]");
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,`]°•○●□■♤♡◇♧☆▪¤《》¡¿,.]");
        Pattern space = Pattern.compile(" ");
        Matcher hasSign = sign.matcher(s);
        Matcher hasCyrillic = cyrillic.matcher(s);
        Matcher hasSpace = space.matcher(s);
        return (hasCyrillic.find() || hasSpace.find() || hasSign.find());
    }

    private void createAccount() {

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(authResult -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(email.getText().toString());
                    userDTO.setId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                    userDTO.setName(name.getText().toString());
                    userDTO.setRole(Role.USER);
                    userDTO.setPassword(password.getText().toString());
                    userDTO.setLastName(lastName.getText().toString());

                    new UserFirestore().setUser(new User().transform(userDTO))
                            .addOnSuccessListener(unused -> startActivity(new Intent(RegistrationMenu.this, MainActivity.class)));
                }).addOnFailureListener(e -> Toast.makeText(getApplication(), "Ошибка регистрации", Toast.LENGTH_SHORT).show());
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_xml:
                startActivity(new Intent(RegistrationMenu.this, MainActivity.class));
                break;
            case R.id.create_account_xml:
                createAccount();
                break;
            case R.id.policy_text:
                createPolicy();
                break;
        }
    }
}