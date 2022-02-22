package com.sharkit.nextmonday.activity;

import static com.sharkit.nextmonday.configuration.validation.Configuration.hasConnection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.activity.service.RegistrationService;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;
import com.sharkit.nextmonday.configuration.adaptive.service.AdaptiveService;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;

public class RegistrationMenu extends AppCompatActivity implements View.OnClickListener {

    private Widget widget;
    private RegistrationService registrationService;

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        widget = Widget.findByView(getWindow().getDecorView());
        AdaptiveService.setRegistrationLayoutAdaptive(TemplateAdaptive.REGISTRATION_MENU, this, widget);
        registrationService = new RegistrationService(this, widget);
        onClickListener();
    }

    private void onClickListener() {
        widget.getButton().getSignIn().setOnClickListener(this);
        widget.getButton().getCreateAccount().setOnClickListener(this);
        widget.getTextView().getPolicyText().setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_xml:
                startActivity(new Intent(RegistrationMenu.this, MainActivity.class));
                break;
            case R.id.create_account_xml:
                if (hasConnection(getApplicationContext()))
                    registrationService.createAccount();
                break;
            case R.id.policy_text_xml:
                registrationService.createPolicy();
                break;
        }
    }
}