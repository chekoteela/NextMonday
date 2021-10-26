package com.sharkit.nextmonday.service.general;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.FEEDBACK;
import static com.sharkit.nextmonday.configuration.constant.ToastMessage.SEND_FEEDBACK;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_EMAIL;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_NAME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.validation.Configuration;
import com.sharkit.nextmonday.entity.feadback.FeedbackDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.util.Calendar;

public class FeedbackService implements LayoutService {
    private Spinner spinner;
    private EditText text;
    private Button send;
    private AdView adView;
    private Context context;

    @SuppressLint("ResourceType")
    @Override
    public LayoutService writeToField() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(context, R.array.spinner_feedback, R.layout.spinner_item);
        spinner.setAdapter(adapter);
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        adView = root.findViewById(R.id.adView);
        send = root.findViewById(R.id.send_sms_xml);
        spinner = root.findViewById(R.id.spinner_xml);
        text = root.findViewById(R.id.text_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        Configuration.showAdView(adView);
        send.setOnClickListener(v -> {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Context.ACCOUNT_SERVICE,Context.MODE_PRIVATE);
            feedbackDTO.setId(sharedPreferences.getString(USER_ID, DEFAULT));
            feedbackDTO.setEmail(sharedPreferences.getString(USER_EMAIL, DEFAULT));
            feedbackDTO.setName(sharedPreferences.getString(USER_NAME, DEFAULT));
            feedbackDTO.setDate(Calendar.getInstance().getTimeInMillis());
            feedbackDTO.setReason(spinner.getSelectedItemPosition());
            feedbackDTO.setText(text.getText().toString());

            FirebaseFirestore.getInstance()
                    .collection(FEEDBACK)
                    .add(feedbackDTO)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(context, SEND_FEEDBACK, Toast.LENGTH_SHORT).show();
                        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.nav_diary);
                    });
        });
        return this;
    }
}
