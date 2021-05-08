package com.sharkit.nextmonday.ui.Calculator;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.DataPFC;


public class Weight extends Fragment {
    final String TAG = "qwerty";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("DB Product");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.calculator_weight, container, false);




        TextView t1 = root.findViewById(R.id.text);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String data = "";
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                            DataPFC dataPFC = documentSnapshot.toObject(DataPFC.class);
//                            String name = dataPFC.getName();
//                            String code = dataPFC.getBar_code();
//                            String id = dataPFC.getID();
//                            String portion = dataPFC.getPortion();
//
//                            data += "Name: " + name +"\nCode: " + code + "\nid: " + id + "\nportion: " + portion+"\n\n";
//
//                        }
//                        Log.d(TAG, data);
//                    }
//                });
//            }
//        });

        return root;
    }
}