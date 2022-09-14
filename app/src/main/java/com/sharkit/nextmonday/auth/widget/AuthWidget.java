package com.sharkit.nextmonday.auth.widget;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.sharkit.nextmonday.R;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthWidget {

    private final View view;

    public static AuthWidget newInstance(final View view) {
        return new AuthWidget(view);
    }

    public RegisterMenuWidget getRegisterMenuWidget() {
        return new RegisterMenuWidget();
    }

    public AuthorisationMenuWidget getAuthorisationMenuWidget() {
        return new AuthorisationMenuWidget();
    }

    public Dialog getDialog() {
        return new Dialog();
    }

    @Getter
    public class RegisterMenuWidget {

        private final EditText userName;
        private final EditText userLastName;
        private final EditText email;
        private final EditText password;
        private final Button createAccount;
        private final Button signIn;
        private final CheckBox policy;
        private final TextView policyText;
        private final TextInputLayout emailLayout;
        private final TextInputLayout passwordLayout;
        private final TextInputLayout userNameLayout;
        private final TextInputLayout userLastNameLayout;

        public RegisterMenuWidget() {
            this.userName = AuthWidget.this.view.findViewById(R.id.user_name_id);
            this.email = AuthWidget.this.view.findViewById(R.id.email_id);
            this.password = AuthWidget.this.view.findViewById(R.id.password_id);
            this.signIn = AuthWidget.this.view.findViewById(R.id.sign_in_id);
            this.policy = AuthWidget.this.view.findViewById(R.id.policy_id);
            this.createAccount = AuthWidget.this.view.findViewById(R.id.create_account_id);
            this.userLastName = AuthWidget.this.view.findViewById(R.id.user_last_name_id);
            this.policyText = AuthWidget.this.view.findViewById(R.id.policy_text_id);
            this.emailLayout = AuthWidget.this.view.findViewById(R.id.email_layout_id);
            this.passwordLayout = AuthWidget.this.view.findViewById(R.id.password_layout_id);
            this.userNameLayout = AuthWidget.this.view.findViewById(R.id.user_name_layout_id);
            this.userLastNameLayout = AuthWidget.this.view.findViewById(R.id.user_last_name_layout_id);
        }
    }

    @Getter
    public class AuthorisationMenuWidget {

        private final Button createAccount;
        private final Button signIn;
        private final EditText email;
        private final EditText password;
        private final ImageView google;
        private final TextView forgotPassword;
        private final TextInputLayout emailLayout;
        private final TextInputLayout passwordLayout;

        public AuthorisationMenuWidget() {
            this.password = AuthWidget.this.view.findViewById(R.id.password_id);
            this.google = AuthWidget.this.view.findViewById(R.id.google_id);
            this.email = AuthWidget.this.view.findViewById(R.id.email_id);
            this.signIn = AuthWidget.this.view.findViewById(R.id.sign_in_id);
            this.createAccount = AuthWidget.this.view.findViewById(R.id.create_account_id);
            this.forgotPassword = AuthWidget.this.view.findViewById(R.id.forgot_password_id);
            this.emailLayout = AuthWidget.this.view.findViewById(R.id.email_layout_id);
            this.passwordLayout = AuthWidget.this.view.findViewById(R.id.password_layout_id);
        }
    }

    @Getter
    public class Dialog {

        private final ResetPasswordWidget resetPasswordWidget;

        public Dialog() {
            this.resetPasswordWidget = new ResetPasswordWidget();
        }

        @Getter
        public class ResetPasswordWidget {
            private final TextInputLayout emailLayout;
            private final EditText email;

            public ResetPasswordWidget() {
                this.emailLayout = AuthWidget.this.view.findViewById(R.id.email_layout_id);
                this.email = AuthWidget.this.view.findViewById(R.id.email_id);
            }
        }

    }
}
