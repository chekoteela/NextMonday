package com.sharkit.nextmonday.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.sharkit.nextmonday.MainActivity.isValidEmail;

public class FragmentEdit extends Fragment {
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference users;
    String Name,mail,lastName;
    final String TAG = "qwerty";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;
        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(-1,h/16);
        l_params.setMargins(h/50,h/85,h/50,h/85);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        users = db.getReference("Users");
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Button save = root.findViewById(R.id.save);
        Button change_pass = root.findViewById(R.id.change_pass);
        EditText name = root.findViewById(R.id.name);
        EditText last_name = root.findViewById(R.id.last_name);
        EditText pass = root.findViewById(R.id.pass);
        EditText email = root.findViewById(R.id.email);

        name.setLayoutParams(l_params);
        save.setLayoutParams(l_params);
        change_pass.setLayoutParams(l_params);
        last_name.setLayoutParams(l_params);
        pass.setLayoutParams(l_params);
        email.setLayoutParams(l_params);

        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        pass.setText("");
        try {
            FirebaseUsers(root);
        }catch (NullPointerException e){}

        if(!hasConnection(getContext())){
            Toast.makeText(getContext(),"Подключение к интернету отсутствует, установите соединение и обновите страницу",Toast.LENGTH_SHORT).show();
            name.setEnabled(false);
            last_name.setEnabled(false);
            email.setEnabled(false);
            save.setEnabled(false);
            change_pass.setEnabled(false);
        }


        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View forgotPass = layoutInflater.inflate(R.layout.resetpassword, null);
                Button btnEmailReset = forgotPass.findViewById(R.id.btnResetPassword);
                EditText EmailReset = forgotPass.findViewById(R.id.EmailReset);
                btnEmailReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(EmailReset.getText().toString())){
                            Toast.makeText(getContext(),"Введите почту",Toast.LENGTH_SHORT).show();

                            return;
                        }
                        mAuth.sendPasswordResetEmail(EmailReset.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(),"Проверте вашу почту",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Почта не найдена",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
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
                if(!email.getText().toString().equals(mail)){
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
               if  (name.getText().toString().equals(Name)) {
                    save.setVisibility(View.GONE);
                    return;
                }
                if(!name.getText().toString().equals(Name)){
                    save.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setVisibility(View.GONE);
               if (last_name.getText().toString().equals(lastName)) {
                    save.setVisibility(View.GONE);
                    return;
                }
                if(!last_name.getText().toString().equals(lastName)){
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
                if(!isValidEmail(email.getText())){
                    Toast.makeText(getContext(),"Введите коректный формат почты",Toast.LENGTH_SHORT).show();
                    return;
                }
                pass.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(pass.getText())){
                    Toast.makeText(getContext(), "Введите пароль для подтверждения", Toast.LENGTH_LONG).show();
                    return;
                }
                AuthCredential credential = EmailAuthProvider
                        .getCredential(mAuth.getCurrentUser().getEmail(), pass.getText().toString());
                firebaseUser.reauthenticate(credential);
            firebaseUser.updateEmail(email.getText().toString().trim())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pass.setVisibility(View.GONE);
                            Users us = new Users();
                            us.setName(name.getText().toString());
                            us.setEmail(email.getText().toString());
                            us.setLastName(last_name.getText().toString());
                            users.child(mAuth.getCurrentUser().getUid()).setValue(us);
                            Toast.makeText(getContext(), "Почта была успешно изменена", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Почта уже занята", Toast.LENGTH_SHORT).show();
                }
            });
            }
        });

        return root;
    }

    public void FirebaseUsers(View root){
        EditText name = root.findViewById(R.id.name);
        EditText last_name = root.findViewById(R.id.last_name);
        EditText email = root.findViewById(R.id.email);
        TextView text = root.findViewById(R.id.text);
        Button save = root.findViewById(R.id.save);
        Button change_pass = root.findViewById(R.id.change_pass);
        users.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                try {
                    email.setText(user.getEmail());
                    last_name.setText(user.getLastName());
                    name.setText(user.getName());
                    mail = user.getEmail();
                    lastName = user.getLastName();
                    Name = user.getName();
                    save.setVisibility(View.GONE);
                }catch (NullPointerException e){}

                if(TextUtils.isEmpty(last_name.getText())){
                    name.setEnabled(false);
                    last_name.setEnabled(false);
                    email.setEnabled(false);
                    save.setEnabled(false);
                    change_pass.setEnabled(false);
                    text.setVisibility(View.VISIBLE);
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }
}