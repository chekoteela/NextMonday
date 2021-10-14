package com.sharkit.nextmonday.service.general;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_EMAIl;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class FeedbackService implements LayoutService {
    private Spinner spinner;
    private EditText name, email, text;
    private Context context;

    @Override
    public LayoutService writeToField() {
        SharedPreferences sharedPreferences = ((Activity) context).getSharedPreferences(Context.ACCOUNT_SERVICE,Context.MODE_PRIVATE);
        email.setText(sharedPreferences.getString(USER_EMAIl, DEFAULT));
        name.setText(sharedPreferences.getString(USER_NAME, DEFAULT));
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        spinner = root.findViewById(R.id.spinner_xml);
        name = root.findViewById(R.id.name_xml);
        email = root.findViewById(R.id.email_xml);
        text = root.findViewById(R.id.text_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        return this;
    }
}
