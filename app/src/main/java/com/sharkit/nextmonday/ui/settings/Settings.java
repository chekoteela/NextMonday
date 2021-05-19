package com.sharkit.nextmonday.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.R;

import org.jetbrains.annotations.NotNull;


public class Settings extends Fragment {

    Button profile, calculator, general, moderator;

    final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings, container, false);

        FindView(root);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

            users.child("moderator").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String k = snapshot.getValue(String.class);
                    if (k == null){
                        moderator.setVisibility(View.GONE);
                    }else {
                    moderator.setVisibility(View.VISIBLE);
                }}


                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
            general.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.nav_settings_general);
                }
            });
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.nav_profile);
                }
            });
            calculator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.nav_cal_auto_calculate_calorie);
                }
            });
            moderator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.nav_settings_list_moderation);
                }
            });





        return root;

    }



    private void FindView(View root) {
        profile = root.findViewById(R.id.profile);
        calculator = root.findViewById(R.id.calculator);
        moderator = root.findViewById(R.id.moderator);
        general = root.findViewById(R.id.general);
    }
}