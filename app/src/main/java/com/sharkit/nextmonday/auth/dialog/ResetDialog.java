package com.sharkit.nextmonday.auth.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.validation.ForgotPasswordDialogValidation;
import com.sharkit.nextmonday.auth.widget.AuthWidget;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressLint("InflateParams")
public class ResetDialog {

    private final Context context;
    private static final String TAG = ResetDialog.class.getCanonicalName();

    public void reset() {
        final AlertDialog dialog = new AlertDialog.Builder(this.context).create();
        final View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_reset_password, null);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final AuthWidget.Dialog.ResetPasswordWidget widget = AuthWidget.newInstance(view).getDialog().getResetPasswordWidget();

        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                this.context.getString(R.string.button_send),
                (dialog1, which) -> {
            if (new ForgotPasswordDialogValidation(this.context, widget).isValidEmail())
            mAuth.sendPasswordResetEmail(widget.getEmail().getText().toString().trim())
                            .addOnSuccessListener(unused -> Toast.makeText(this.context, "Check your email", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Log.e(TAG, e.getMessage(), e));
                });

        dialog.setView(view);
        dialog.show();
    }
}
