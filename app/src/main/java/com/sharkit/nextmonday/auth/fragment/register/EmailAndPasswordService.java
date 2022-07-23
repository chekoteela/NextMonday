package com.sharkit.nextmonday.auth.fragment.register;

import static com.sharkit.nextmonday.auth.fragment.UserTransformer.toUser;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isNameValid;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isPasswordValid;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isValidEmail;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.configuration.widget_finder.layout.RegisterMenuWidget;
import com.sharkit.nextmonday.auth.entity.User;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EmailAndPasswordService {

    private static final String TAG = EmailAndPasswordService.class.getCanonicalName();
    private final RegisterMenuWidget widgetContainer;

    public EmailAndPasswordService(RegisterMenuWidget widgetContainer) {
        this.widgetContainer = widgetContainer;
    }

    public void createAccountByEmailAndPassword() {
        if (Boolean.FALSE.equals(validateField())) {
            return;
        }

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final User user = toUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                widgetContainer.email().getText().toString(),
                widgetContainer.userName().getText().toString(),
                widgetContainer.userLastName().getText().toString(),
                widgetContainer.password().getText().toString());

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(authResult -> {
                    Log.i(TAG, String.format("User: %s is created", user));
                    new UserRepository().create(user);
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getMessage(), e));
    }

    private Boolean validateField() {

        return isValidEmail(widgetContainer.email().getText().toString()) &&
                isNameValid(widgetContainer.userName().getText().toString()) &&
                isNameValid(widgetContainer.userLastName().getText().toString()) &&
                isPasswordValid(widgetContainer.password().getText().toString());
    }


}
