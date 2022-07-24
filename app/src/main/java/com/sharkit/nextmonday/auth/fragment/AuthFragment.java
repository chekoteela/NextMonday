package com.sharkit.nextmonday.auth.fragment;

import static com.sharkit.nextmonday.configuration.validation.field_validation.AuthValidation.isValidAuthField;

import android.content.Intent;
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
import com.sharkit.nextmonday.auth.fragment.register.GoogleRegistration;
import com.sharkit.nextmonday.configuration.utils.CryptoAES;
import com.sharkit.nextmonday.configuration.utils.ToastMenuMessage;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.configuration.widget_finder.layout.AuthorisationMenuWidget;
import com.sharkit.nextmonday.main_menu.NavigationMenu;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AuthFragment extends Fragment {

    private static final String TAG = AuthFragment.class.getCanonicalName();
    private AuthorisationMenuWidget widgetContainer;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.authorisation_menu, container, false);

        mAuth = FirebaseAuth.getInstance();
        widgetContainer = WidgetContainer.newInstance(view).getAuthorisationMenuWidget();

        widgetContainer.createAccount().setOnClickListener(v -> createAccount());
        widgetContainer.signIn().setOnClickListener(v -> authByEmailAndPassword());
        widgetContainer.google().setOnClickListener(v -> new GoogleRegistration(getActivity()).signIn());
        return view;
    }

    private void createAccount() {
        Navigation.findNavController(requireActivity(), R.id.start_navigation).navigate(R.id.nav_register_fragment);
    }

    private void authByEmailAndPassword() {
        if (Boolean.TRUE.equals(isValidAuthData())) {
            return;
        }

        final CryptoAES aes = CryptoAES.getInstance();

        mAuth.signInWithEmailAndPassword(widgetContainer.email().getText().toString(), aes.encrypt(widgetContainer.password().getText().toString().trim()))
                .addOnSuccessListener(authResult -> startActivity(new Intent(getActivity(), NavigationMenu.class))).addOnFailureListener(e -> {
            ToastMenuMessage.trowToastMessage();
            Log.i(TAG, e.getMessage(), e);
        });
    }

    private Boolean isValidAuthData() {
        return isValidAuthField(widgetContainer.email().getText().toString()) &&
                isValidAuthField(widgetContainer.password().getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), NavigationMenu.class));
        }
    }
}
