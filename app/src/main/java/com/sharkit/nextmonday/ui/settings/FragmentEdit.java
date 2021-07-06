package com.sharkit.nextmonday.ui.settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.FirebaseEntity.SettingProfile;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.Users;

import org.jetbrains.annotations.NotNull;

import kotlin.sequences.USequencesKt;

public class FragmentEdit extends Fragment implements View.OnClickListener {
    private EditText name, lastName, email;
    private TextView textView;
    private Button save, changePass;
    private Users users;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref = db.getReference("Users/" + mAuth.getCurrentUser().getUid());
    final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        Adaptive();
        getCurrentUser();
        findViewById(root);
        onClick();
        return root;
    }

    public void writeToFields(Users users) {
        name.setText(users.getName());
        lastName.setText(users.getLastName());
        email.setText(users.getEmail());
    }

    private void onClick() {
        save.setOnClickListener(this);
        changePass.setOnClickListener(this);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!name.getText().toString().equals(users.getName())) {
                    save.setVisibility(View.VISIBLE);
                } else save.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!lastName.getText().toString().equals(users.getLastName())) {
                    save.setVisibility(View.VISIBLE);
                } else save.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!email.getText().toString().equals(users.getEmail())) {
                    save.setVisibility(View.VISIBLE);
                } else save.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void findViewById(View root) {
        name = root.findViewById(R.id.name_xml);
        lastName = root.findViewById(R.id.last_name);
        email = root.findViewById(R.id.email_xml);
        textView = root.findViewById(R.id.text_xml);
        save = root.findViewById(R.id.save_xml);
        changePass = root.findViewById(R.id.change_pass);
    }

    private void getCurrentUser() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                users = snapshot.getValue(Users.class);
                writeToFields(users);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private void Adaptive() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;
        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(-1, h / 16);
        l_params.setMargins(h / 50, h / 85, h / 50, h / 85);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_xml:
                break;
            case R.id.change_pass:
                break;
        }
    }
}