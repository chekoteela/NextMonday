package com.sharkit.nextmonday.ui.diary;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_MAIN_DIARY_LIST;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_EMAIl;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_ID;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_LAST_NAME;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_NAME;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_PASSWORD;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.USER_ROLE;
import static com.sharkit.nextmonday.configuration.constant.CollectionUser.USERS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.MainActivity;
import com.sharkit.nextmonday.MainMenu;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.entity.user.UserPreferenceDTO;
import com.sharkit.nextmonday.service.diary.main_diary_service.MainDiaryService;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class MainDiary extends Fragment {
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy, container, false);

        MainDiaryService mainDiaryService;
        try {
            mainDiaryService = new MainDiaryService(requireArguments().getLong(DATE_FOR_MAIN_DIARY_LIST));
        } catch (IllegalStateException e) {
            mainDiaryService = new MainDiaryService(Calendar.getInstance().getTimeInMillis());
        }
        mainDiaryService.findById(root)
                .setAdaptive()
                .writeToField()
                .activity();
        return root;
    }
}
