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
import com.sharkit.nextmonday.auth.dialog.ResetDialog;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.auth.fb_repository.UserRepository;
import com.sharkit.nextmonday.auth.fragment.register.GoogleRegistration;
import com.sharkit.nextmonday.auth.validation.AuthValidation;
import com.sharkit.nextmonday.auth.widget.AuthWidget;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.main_menu.NavigationMenu;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AuthFragment extends Fragment {

    private static final String TAG = AuthFragment.class.getCanonicalName();
    private AuthWidget.AuthorisationMenuWidget widget;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.authorisation_menu, container, false);

        this.mAuth = FirebaseAuth.getInstance();
        this.widget = AuthWidget.newInstance(view).getAuthorisationMenuWidget();

        this.widget.getCreateAccount().setOnClickListener(v -> createAccount());
        this.widget.getSignIn().setOnClickListener(v -> authByEmailAndPassword());
        this.widget.getGoogle().setOnClickListener(v -> new GoogleRegistration(getActivity()).signIn());
        this.widget.getForgotPassword().setOnClickListener(v -> new ResetDialog(getContext()).reset());
        return view;
    }

    private void createAccount() {
        Navigation.findNavController(requireActivity(), R.id.start_navigation).navigate(R.id.nav_register_fragment);
    }

    private void authByEmailAndPassword() {
        if (!new AuthValidation(getContext(), widget).isValidAuthData()) {
            Log.e(TAG, "Not valid auth data");
            return;
        }

        Log.i(TAG, "auth by email and password");

        this.mAuth.signInWithEmailAndPassword(this.widget.getEmail().getText().toString().trim(),
                        this.widget.getPassword().getText().toString().trim())
                .addOnSuccessListener(authResult -> startActivity(new Intent(getActivity(), NavigationMenu.class)))
                .addOnFailureListener(e -> Log.e(TAG, e.getMessage(), e));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.mAuth.getCurrentUser() != null) {
            UserRepository.getInstance(getContext()).findById(this.mAuth.getCurrentUser().getUid())
                    .addOnSuccessListener(documentSnapshot -> {
                        User currentUser = documentSnapshot.toObject(User.class);
                        new UserSharedPreference(getContext()).set(currentUser);
                        startActivity(new Intent(getActivity(), NavigationMenu.class));
                    });
        }
    }
}
