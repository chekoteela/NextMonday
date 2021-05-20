package com.sharkit.nextmonday.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.Adapters.ModerationAdapter;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.DataPFC;

import java.util.ArrayList;


public class ListModeration extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.set_list_moderation, container, false);

        ListView list = root.findViewById(R.id.list_moderation);

        ArrayList<DataPFC> group = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("DB for moderation");

        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    DataPFC dataPFC = queryDocumentSnapshot.toObject(DataPFC.class);
                    group.add(dataPFC);
                }
                ModerationAdapter adapter = new ModerationAdapter(getContext(), group);
                list.setAdapter(adapter);
            }
        });


        return root;
    }
}