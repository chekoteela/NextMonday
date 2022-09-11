package com.sharkit.nextmonday.auth.fragment.register;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.auth.widget.AuthWidget;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.sharkit.nextmonday.auth.transformer.UserTransformer.toUser;

@RequiredArgsConstructor
@RequiresApi(api = Build.VERSION_CODES.O)
public class EmailAndPasswordService {

    private static final String TAG = EmailAndPasswordService.class.getCanonicalName();

    private final AuthWidget.RegisterMenuWidget widget;
    private final Context context;

    public void createAccountByEmailAndPassword() {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final User user = toUser(
                this.widget.getEmail().getText().toString().trim(),
                this.widget.getUserName().getText().toString().trim(),
                this.widget.getUserLastName().getText().toString().trim(),
                this.widget.getPassword().getText().toString().trim());

        mAuth.createUserWithEmailAndPassword(user.getEmail(), this.widget.getPassword().getText().toString().trim())
                .addOnSuccessListener(authResult -> {
                    user.setId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

                    Log.i(TAG, String.format("User: %s is created", user));

                    UserRepository.getInstance(this.context).create(user);
                    new UserSharedPreference(this.context).set(user);
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getMessage(), e));
    }
}
