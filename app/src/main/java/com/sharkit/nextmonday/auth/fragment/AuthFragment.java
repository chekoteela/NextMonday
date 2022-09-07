package com.sharkit.nextmonday.auth.fragment;

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
import com.sharkit.nextmonday.auth.dialog.AuthDialog;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.auth.fragment.register.GoogleRegistration;
import com.sharkit.nextmonday.auth.validation.AuthValidation;
import com.sharkit.nextmonday.configuration.utils.CryptoAES;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
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
        widgetContainer.getForgotPassword().setOnClickListener(v -> new AuthDialog(getContext()).showChangePasswordDialog());
        return view;
    }

    private void createAccount() {
        Navigation.findNavController(requireActivity(), R.id.start_navigation).navigate(R.id.nav_register_fragment);
    }

    private void authByEmailAndPassword() {
        if (!new AuthValidation(getContext(), widgetContainer).isValidAuthData()) {
            Log.e(TAG, "Not valid auth data");
            return;
        }
        final CryptoAES aes = CryptoAES.getInstance();

        Log.i(TAG, "auth by email and password");

        mAuth.signInWithEmailAndPassword(widgetContainer.getEmail().getText().toString(),
                aes.encrypt(widgetContainer.getPassword().getText().toString().trim()))
                .addOnSuccessListener(authResult -> startActivity(new Intent(getActivity(), NavigationMenu.class)))
                .addOnFailureListener(e -> Log.e(TAG, e.getMessage(), e));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            UserRepository.getInstance(getContext()).findById(mAuth.getCurrentUser().getUid())
                    .addOnSuccessListener(documentSnapshot -> {
                        User currentUser = documentSnapshot.toObject(User.class);
                        new UserSharedPreference(getContext()).set(currentUser);
                        startActivity(new Intent(getActivity(), NavigationMenu.class));
                    });
        }
    }
}
