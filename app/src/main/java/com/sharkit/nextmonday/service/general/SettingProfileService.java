package com.sharkit.nextmonday.service.general;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SEND;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_EMAIl;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_ID;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_LAST_NAME;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_NAME;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_PASSWORD;
import static com.sharkit.nextmonday.configuration.constant.CollectionUser.USERS;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.EMAIL_UPDATED;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.PASSWORDS_NOT_THE_SAME;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.PASSWORD_NOT_CORRECT;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.SUCCESSFUL_UPDATE;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.USER_WITH_EMAIL_EXIST;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.UserFirebaseEntity.EMAIL;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.UserFirebaseEntity.LAST_NAME;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.UserFirebaseEntity.NAME;
import static com.sharkit.nextmonday.configuration.constant.firebase_entity.UserFirebaseEntity.PASSWORD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.configuration.validation.validation_field.ValidationField;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Objects;

public class SettingProfileService implements LayoutService {
    private EditText name, email, lastName, newPassword, confirmPassword, password;
    private Button save, changePassword;
    private Context context;
    private AdView adView;

    @Override
    public LayoutService writeToField() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString(USER_NAME, DEFAULT));
        email.setText(sharedPreferences.getString(USER_EMAIl, DEFAULT));
        lastName.setText(sharedPreferences.getString(USER_LAST_NAME, DEFAULT));
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        adView = root.findViewById(R.id.adView);
        name = root.findViewById(R.id.name_xml);
        email = root.findViewById(R.id.email_xml);
        lastName = root.findViewById(R.id.last_name_xml);
        save = root.findViewById(R.id.save_xml);
        changePassword = root.findViewById(R.id.change_password_xml);
        password = root.findViewById(R.id.password_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        Configuration.showAdView(adView);
        save.setOnClickListener(v -> {
            if (!Configuration.hasConnection(context)){
                return;
            }
                saveUpdates();
        });
        changePassword.setOnClickListener(v -> showForgotPassForm());
        changesInEdit();
        return this;
    }

    private void saveUpdates() {
        if (!ValidationField.isValidName(name, context)) {
            return;
        }
        if (!ValidationField.isValidName(lastName, context)) {
            return;
        }
        if (!ValidationField.isValidEmail(email, context)) {
            return;
        }
        if (!email.getText().toString().equals(context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_EMAIl, DEFAULT))) {
            if (!ValidationField.isValidField(password, context)) {
                return;
            }
            if (!password.getText().toString().equals(context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_PASSWORD, DEFAULT))) {
                Toast.makeText(context, PASSWORD_NOT_CORRECT, Toast.LENGTH_SHORT).show();
                return;
            }
            AuthCredential credential = EmailAuthProvider
                    .getCredential(context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_EMAIl, DEFAULT),
                            context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_PASSWORD, DEFAULT));

                    Objects.requireNonNull(FirebaseAuth.getInstance()
                            .getCurrentUser())
                            .reauthenticate(credential)
                            .addOnSuccessListener(unused -> FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .updateEmail(email.getText().toString())
                                    .addOnSuccessListener(unused1 -> Toast.makeText(context, EMAIL_UPDATED, Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(context, USER_WITH_EMAIL_EXIST, Toast.LENGTH_SHORT).show()));
        }

        FirebaseFirestore.getInstance()
                .collection(USERS)
                .document(context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT))
                .update(NAME, name.getText().toString().trim(),
                        LAST_NAME, lastName.getText().toString().trim(),
                        EMAIL, email.getText().toString().trim())
                .addOnSuccessListener(unused -> {
                    Toast.makeText(context, SUCCESSFUL_UPDATE, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
                })
                .addOnFailureListener(e -> Toast.makeText(context, SUCCESSFUL_UPDATE, Toast.LENGTH_SHORT).show());
    }

    private void changesInEdit() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!name.getText().toString().equals(sharedPreferences.getString(USER_NAME, DEFAULT))) {
                    save.setVisibility(View.VISIBLE);
                } else if (name.getText().toString().equals(sharedPreferences.getString(USER_NAME, DEFAULT)) &&
                        lastName.getText().toString().equals(sharedPreferences.getString(USER_LAST_NAME, DEFAULT)) &&
                        email.getText().toString().equals(sharedPreferences.getString(USER_EMAIl, DEFAULT))) {
                    save.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!email.getText().toString().equals(sharedPreferences.getString(USER_EMAIl, DEFAULT))) {
                    save.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                } else if (name.getText().toString().equals(sharedPreferences.getString(USER_NAME, DEFAULT)) &&
                        lastName.getText().toString().equals(sharedPreferences.getString(USER_LAST_NAME, DEFAULT)) &&
                        email.getText().toString().equals(sharedPreferences.getString(USER_EMAIl, DEFAULT))) {
                    save.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!lastName.getText().toString().equals(sharedPreferences.getString(USER_LAST_NAME, DEFAULT))) {
                    save.setVisibility(View.VISIBLE);
                } else if (name.getText().toString().equals(sharedPreferences.getString(USER_NAME, DEFAULT)) &&
                        lastName.getText().toString().equals(sharedPreferences.getString(USER_LAST_NAME, DEFAULT)) &&
                        email.getText().toString().equals(sharedPreferences.getString(USER_EMAIl, DEFAULT))) {
                    save.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("InflateParams")
    public void showForgotPassForm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View root = LayoutInflater.from(context).inflate(R.layout.resetpassword, null);
        findViewOnDialog(root);
        dialog.setPositiveButton(SEND, (dialog1, which) -> {
            if (!Configuration.hasConnection(context)){
                return;
            }
            if (!ValidationField.isValidField(newPassword, context) || !ValidationField.isValidField(confirmPassword, context)) {
                return;
            }
            if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                Toast.makeText(context, PASSWORDS_NOT_THE_SAME, Toast.LENGTH_SHORT).show();
                return;
            }
            AuthCredential credential = EmailAuthProvider
                    .getCredential(context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_EMAIl, DEFAULT),
                            context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_PASSWORD, DEFAULT));

            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
            .reauthenticate(credential)
                    .addOnSuccessListener(unused -> FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .updatePassword(newPassword.getText().toString())
                            .addOnSuccessListener(unused1 -> {
                                FirebaseFirestore.getInstance().collection(USERS)
                                        .document(context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT))
                                        .update(PASSWORD, newPassword.getText().toString().trim());
                                Toast.makeText(context, SUCCESSFUL_UPDATE, Toast.LENGTH_SHORT).show();
                                Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
                            }));
        });
        dialog.setView(root);
        dialog.show();
    }

    private void findViewOnDialog(View root) {
        newPassword = root.findViewById(R.id.password_xml);
        confirmPassword = root.findViewById(R.id.confirm_password_xml);
    }
}
