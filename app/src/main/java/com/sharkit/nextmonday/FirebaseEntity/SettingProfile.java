package com.sharkit.nextmonday.FirebaseEntity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.Users.Users;
import com.sharkit.nextmonday.ui.settings.FragmentEdit;

import org.jetbrains.annotations.NotNull;

public class SettingProfile {
    private Users users;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref = db.getReference("Users/" + mAuth.getCurrentUser().getUid());

    public void getCurrentUser() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                users = snapshot.getValue(Users.class);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }
}
