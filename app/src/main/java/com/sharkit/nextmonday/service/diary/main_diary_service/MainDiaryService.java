package com.sharkit.nextmonday.service.diary.main_diary_service;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.FirebaseCollection.USERS;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_EMAIl;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_LAST_NAME;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_NAME;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_PASSWORD;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ROLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.diary.MainDiaryAdapter;
import com.sharkit.nextmonday.db.firestore.diary.DiaryFirestore;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.user.UserPreferenceDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainDiaryService implements LayoutService {
    private final long date;
    private ExpandableListView listView;
    private TargetDataService dataService;
    private Context context;

    public MainDiaryService(long date) {
        this.date = date;
    }

    @Override
    public LayoutService writeToField() {
        dataService = new TargetDataService(context);
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        listView = root.findViewById(R.id.expandable_list_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        setSharedPreference();
        synchronizedDB();
        listView.setAdapter(new MainDiaryAdapter(context, dataService.getWeekList(date)));
        return this;
    }

    private void synchronizedDB() {
        TargetDataService service = new TargetDataService(context);
        DiaryFirestore diaryFirestore = new DiaryFirestore();
        diaryFirestore.synchronizedDB(service);
        service.synchronizedDB();
    }

    @SuppressLint("SimpleDateFormat")
    private void setSharedPreference() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Context.ACCOUNT_SERVICE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FirebaseFirestore.getInstance().collection(USERS)
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserPreferenceDTO userDTO = documentSnapshot.toObject(UserPreferenceDTO.class);
                    editor.putString(USER_ID, Objects.requireNonNull(userDTO).getId());
                    editor.putString(USER_LAST_NAME, Objects.requireNonNull(userDTO).getLastName());
                    editor.putString(USER_NAME, Objects.requireNonNull(userDTO).getName());
                    editor.putString(USER_EMAIl, Objects.requireNonNull(userDTO).getEmail());
                    editor.putString(USER_PASSWORD, Objects.requireNonNull(userDTO).getPassword());
                    editor.putString(USER_ROLE, Objects.requireNonNull(userDTO).getRole());
                    editor.apply();
                });
        dataService.getAlarm(new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()));
    }

}
