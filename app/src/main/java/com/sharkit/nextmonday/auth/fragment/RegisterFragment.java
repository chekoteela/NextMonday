package com.sharkit.nextmonday.auth.fragment;

import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isNameValid;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isPasswordValid;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isValidEmail;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.configuration.utils.CryptoAES;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.configuration.widget_finder.layout.RegisterMenuWidget;
import com.sharkit.nextmonday.entity.auth.User;
import com.sharkit.nextmonday.entity.auth.enums.UserRole;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RegisterFragment extends Fragment {

    private RegisterMenuWidget widgetContainer;
    private FirebaseAuth mAuth;
    public static final String TAG = RegisterFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.registration_menu, container, false);
        mAuth = FirebaseAuth.getInstance();

        widgetContainer = WidgetContainer.newInstance(view).getRegisterMenuWidget();

        widgetContainer.signIn().setOnClickListener(v -> returnToAuthFragment());
        widgetContainer.createAccount().setOnClickListener(v -> createAccountByEmailAndPassword());

        return view;
    }

    private void returnToAuthFragment() {
        Navigation.findNavController(requireActivity(), R.id.start_navigation).navigate(R.id.nav_auth_fragment);
    }

    private void createAccountByEmailAndPassword() {
        if (Boolean.FALSE.equals(validateField())) {
            return;
        }

        CryptoAES aes = CryptoAES.getInstance();

        User user = User.builder()
                .id(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .email(widgetContainer.email().getText().toString().trim())
                .name(widgetContainer.userName().getText().toString().trim())
                .lastName(widgetContainer.userLastName().getText().toString().trim())
                .password(aes.encrypt(widgetContainer.password().getText().toString().trim()))
                .role(UserRole.USER)
                .build();

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(authResult -> {
                    Log.i(TAG, String.format("User: %s is created", user));
                    new UserRepository(requireActivity()).create(user);
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