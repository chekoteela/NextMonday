package com.sharkit.nextmonday.diary.ui;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.Widget;
import com.sharkit.nextmonday.diary.adapter.DiaryMainListAdapter;
import com.sharkit.nextmonday.diary.db.firebase.DiaryTaskRepository;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;
import com.sharkit.nextmonday.diary.entity.DiaryTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.SneakyThrows;

public class DiaryMain extends Fragment {

    private static final String DATE_FORMAT = "%s.%s";

    @SneakyThrows
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_main, container, false);
        Widget widget = Widget.findByView(view);
        DiaryTaskRepository repository = new DiaryTaskRepository(requireContext());
        Calendar calendar = Calendar.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        List<List<DiaryTask>> diaryTasks = new ArrayList<>();

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        for (int i = 0; i < 7; i++) {
            List<DiaryTask> tasks = new ArrayList<>();
            repository.getTaskOnWeek(firebaseAuth.getCurrentUser().getUid(), String.format(DATE_FORMAT,
                    calendar.get(Calendar.WEEK_OF_YEAR), calendar.get(Calendar.YEAR)))
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                                tasks.add(queryDocumentSnapshot.toObject(DiaryTask.class));
                            }
                        }
                    });
            diaryTasks.add(tasks);
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        widget.getExpandableList().getExpandableListView().setAdapter(new DiaryMainListAdapter(getContext(), diaryTasks));

        return view;
    }

}
