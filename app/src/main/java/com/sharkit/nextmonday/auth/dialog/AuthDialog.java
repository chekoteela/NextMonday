package com.sharkit.nextmonday.auth.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDialog {

    private final Context context;

    public void showChangePasswordDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
    }
}
