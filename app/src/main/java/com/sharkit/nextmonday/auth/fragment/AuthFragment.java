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
import com.sharkit.nextmonday.main_menu.NavigationMenu;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AuthFragment extends Fragment {

    private static final String TAG = AuthFragment.class.getCanonicalName();
    private WidgetContainer.AuthorisationMenuWidget widgetContainer;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.authorisation_menu, container, false);

        mAuth = FirebaseAuth.getInstance();
        widgetContainer = WidgetContainer.newInstance(view).getAuthorisationMenuWidget();

        widgetContainer.getCreateAccount().setOnClickListener(v -> createAccount());
        widgetContainer.getSignIn().setOnClickListener(v -> authByEmailAndPassword());
        widgetContainer.getGoogle().setOnClickListener(v -> new GoogleRegistration(getActivity()).signIn());
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

        mAuth.signInWithEmailAndPassword(widgetContainer.getEmail().getText().toString(),
                aes.encrypt(widgetContainer.getPassword().getText().toString().trim()))
                .addOnSuccessListener(authResult -> startActivity(new Intent(getActivity(), NavigationMenu.class))).addOnFailureListener(e -> {
            ToastMenuMessage.trowToastMessage();
            Log.i(TAG, e.getMessage(), e);
        });
    }

    private Boolean isValidAuthData() {
        return isValidAuthField(widgetContainer.getEmail().getText().toString()) &&
                isValidAuthField(widgetContainer.getPassword().getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), NavigationMenu.class));
        }
    }
}
