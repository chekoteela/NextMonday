package com.sharkit.nextmonday.auth.fragment.register;

import static com.sharkit.nextmonday.auth.transformer.UserTransformer.toUser;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

import java.util.Objects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequiresApi(api = Build.VERSION_CODES.O)
public class EmailAndPasswordService {

    private static final String TAG = EmailAndPasswordService.class.getCanonicalName();

    private final WidgetContainer.RegisterMenuWidget widgetContainer;
    private final Context context;

    public void createAccountByEmailAndPassword() {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final User user = toUser(
                widgetContainer.getEmail().getText().toString(),
                widgetContainer.getUserName().getText().toString(),
                widgetContainer.getUserLastName().getText().toString(),
                widgetContainer.getPassword().getText().toString());

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(authResult -> {
                    user.setId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

                    Log.i(TAG, String.format("User: %s is created", user));

                    UserRepository.getInstance(context).create(user);
                    new UserSharedPreference(context).set(user);
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getMessage(), e));
    }
}
