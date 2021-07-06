package com.sharkit.nextmonday.ui.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.Configuration.Configuration;
import com.sharkit.nextmonday.Exception.CustomToastException;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.Users;

import org.jetbrains.annotations.NotNull;

import static com.sharkit.nextmonday.MainActivity.isValidEmail;

public class FragmentEdit extends Fragment {
    private Button save, changPass;
    private EditText name, last_Name, pass, email;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference users = db.getReference("Users");
    private FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private AdView mAdView;
    private String Name, mail, lastName, password;
    private TextView text;
    private final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        findView(root);
        Adaptive();
        onClick();
        AdNewView();


        pass.setText("");
        try {
            FirebaseUsers();
        } catch (NullPointerException e) {
        }

        if (!Configuration.hasConnection(getContext())) {
            Toast.makeText(getContext(), "Подключение к интернету отсутствует, установите соединение и обновите страницу", Toast.LENGTH_SHORT).show();
            name.setEnabled(false);
            last_Name.setEnabled(false);
            email.setEnabled(false);
            save.setEnabled(false);
            changPass.setEnabled(false);
        }


        return root;
    }

    private void AdNewView() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void onClick() {
        changPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View forgotPass = layoutInflater.inflate(R.layout.resetpassword, null);
                Button btnEmailReset = forgotPass.findViewById(R.id.btnResetPassword);
                EditText EmailReset = forgotPass.findViewById(R.id.EmailReset);

                btnEmailReset.setOnClickListener(v1 -> {
                    if (TextUtils.isEmpty(EmailReset.getText().toString())) {
                        Toast.makeText(getContext(), "Введите почту", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAuth.sendPasswordResetEmail(EmailReset.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Проверте вашу почту", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Почта не найдена", Toast.LENGTH_SHORT).show();

                        }
                    });
                });
                dialog.setView(forgotPass);
                dialog.show();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (email.getText().toString().equals(mail)) {
                    save.setVisibility(View.GONE);

                }
                if (!email.getText().toString().equals(mail)) {
                    save.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setVisibility(View.GONE);
                if (name.getText().toString().equals(Name)) {
                    save.setVisibility(View.GONE);
                    return;
                }
                if (!name.getText().toString().equals(Name)) {
                    save.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        last_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setVisibility(View.GONE);
                if (last_Name.getText().toString().equals(lastName)) {
                    save.setVisibility(View.GONE);
                    return;
                }
                if (!last_Name.getText().toString().equals(lastName)) {
                    save.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setVisibility(View.VISIBLE);
                if (!isValidEmail(email.getText())) {
                    Toast.makeText(getContext(), "Введите коректный формат почты", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass.getText())) {
                    Toast.makeText(getContext(), "Введите пароль для подтверждения", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d(TAG, password);
                if (!pass.getText().toString().equals(password)){
                    try {
                        throw new CustomToastException(getContext(), "Пароль не верный");
                    } catch (CustomToastException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                changeEmail();
            }
        });
    }

    private void changeEmail() {

        firebaseUser.updateEmail(email.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
    }

    private void Adaptive() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;
        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(-1, h / 16);
        l_params.setMargins(h / 50, h / 85, h / 50, h / 85);


        name.setLayoutParams(l_params);
        save.setLayoutParams(l_params);
        changPass.setLayoutParams(l_params);
        last_Name.setLayoutParams(l_params);
        pass.setLayoutParams(l_params);
        email.setLayoutParams(l_params);
    }

    private void findView(View root) {
        mAdView = root.findViewById(R.id.adView);
        save = root.findViewById(R.id.save_xml);
        changPass = root.findViewById(R.id.change_pass);
        name = root.findViewById(R.id.name_xml);
        last_Name = root.findViewById(R.id.last_name);
        pass = root.findViewById(R.id.pass);
        email = root.findViewById(R.id.email_xml);
        text = root.findViewById(R.id.text_xml);
    }

    public void FirebaseUsers() {
        Log.d(TAG, "user"   );
        users.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                try {

                    password = user.getPassword();
                    email.setText(user.getEmail());
                    last_Name.setText(user.getLastName());
                    name.setText(user.getName());
                    mail = user.getEmail();
                    lastName = user.getLastName();
                    Name = user.getName();
                    save.setVisibility(View.GONE);
                }catch (NullPointerException e){
                    name.setEnabled(false);
                    last_Name.setEnabled(false);
                    email.setEnabled(false);
                    save.setEnabled(false);
                    changPass.setEnabled(false);
                    text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}