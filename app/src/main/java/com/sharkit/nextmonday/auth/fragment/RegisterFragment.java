package com.sharkit.nextmonday.auth.fragment;

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

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.fragment.register.EmailAndPasswordService;
import com.sharkit.nextmonday.auth.validation.RegistrationMenuValidation;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getCanonicalName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.registration_menu, container, false);
        final WidgetContainer.RegisterMenuWidget widgetContainer = WidgetContainer.newInstance(view).getRegisterMenuWidget();

        widgetContainer.getSignIn().setOnClickListener(v -> returnToAuthFragment());
        widgetContainer.getCreateAccount().setOnClickListener(v -> createAccountByEmailAndPassword(widgetContainer));

        return view;
    }

    private void returnToAuthFragment() {
        Log.i(TAG, "Moving to auth fragment");
        Navigation.findNavController(requireActivity(), R.id.start_navigation).navigate(R.id.nav_auth_fragment);
    }

    private void createAccountByEmailAndPassword(WidgetContainer.RegisterMenuWidget widget) {
        if (!new RegistrationMenuValidation(widget, getContext()).validateField()){
            Log.e(TAG, "Not valid registration data");
            return;
        }
        EmailAndPasswordService service = new EmailAndPasswordService(widget, getContext());
        Log.i(TAG, "Moving to creation user by email and password");
        service.createAccountByEmailAndPassword();
    }
}