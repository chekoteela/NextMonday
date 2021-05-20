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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ModeratorChange extends Fragment {

    TextView potassium, salt, calcium, cellulose, watter, casein_protein, agg_protein, soy_protein, whey_protein,
            protein, complex_carbohydrate, simple_carbohydrate, carbohydrate, epa, dha, ala,
            omega3, omega6, omega9, trans_fat, saturated_fat, fat, name, portion, calorie,
            potassium_text, salt_text, calcium_text, cellulose_text, watter_text, casein_protein_text,
            agg_protein_text, soy_protein_text, whey_protein_text, protein_text, complex_carbohydrate_text,
            simple_carbohydrate_text, carbohydrate_text, epa_text, dha_text, ala_text,
            omega3_text, omega6_text, omega9_text, trans_fat_text, saturated_fat_text, fat_text, calorie_text,
            portion_text, name_text;
    Button save;

    ArrayList<String> tags;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collRef = db.collection("DB Product");
    CollectionReference deleteDoc = db.collection("DB for moderation");
    final String TAG = "qwerty";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.set_moderator_change, container, false);

        FindView(root);
        WritList();
        AuditBarCode();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateKey(name_text.getText().toString().trim());
                deleteDoc.whereEqualTo("bar_code", LocalDataPFC.getBar_code()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                            QueryDocumentSnapshot document = change.getDocument();
                            deleteDoc.document(document.getId()).delete();

                        }
                    }
                });
                collRef.add(WriteForFS()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_settings_list_moderation);
                    }
                });
            }
        });

        return root;
    }

    private void generateKey(String inputText) {
        String inputString = inputText.toLowerCase();
        String [] tagArray = inputString.split(" ");
        tags = new ArrayList<>();



        for (String word : tagArray){
            String a = "";
            char [] b = inputString.toCharArray();

            for (int i = 0; i < b.length; i++){
                a += b[i];
                tags.add(a);
            }
            inputString = inputString.replace(word, "").trim();
        }

    }

    private DataPFC WriteForFS (){
        DataPFC dataPFC = new DataPFC();

        dataPFC.setID(LocalDataPFC.getID());
        dataPFC.setName(name_text.getText().toString());
        dataPFC.setTag(tags);
        dataPFC.setBar_code(LocalDataPFC.getBar_code());
        dataPFC.setCalorie(calorie_text.getText().toString());
        dataPFC.setPortion(portion_text.getText().toString());
        dataPFC.setFat(fat_text.getText().toString());
        dataPFC.setSaturated_fat(saturated_fat_text.getText().toString());
        dataPFC.setTrans_fat(trans_fat_text.getText().toString());
        dataPFC.setOmega_9(omega9_text.getText().toString());
        dataPFC.setOmega_6(omega6_text.getText().toString());
        dataPFC.setOmega_3(omega3_text.getText().toString());
        dataPFC.setAla(ala_text.getText().toString());
        dataPFC.setDha(dha_text.getText().toString());
        dataPFC.setEpa(epa_text.getText().toString());
        dataPFC.setCarbohydrate(carbohydrate_text.getText().toString());
        dataPFC.setSimple_carbohydrates(simple_carbohydrate_text.getText().toString());
        dataPFC.setComplex_carbohydrate(complex_carbohydrate_text.getText().toString());
        dataPFC.setProtein(protein_text.getText().toString());
        dataPFC.setCasein_protein(casein_protein_text.getText().toString());
        dataPFC.setAgg_protein(agg_protein_text.getText().toString());
        dataPFC.setSoy_protein(soy_protein_text.getText().toString());
        dataPFC.setWhey_protein(whey_protein_text.getText().toString());
        dataPFC.setCellulose(cellulose_text.getText().toString());
        dataPFC.setWatter(watter_text.getText().toString());
        dataPFC.setSalt(salt_text.getText().toString());
        dataPFC.setCalcium(calcium_text.getText().toString());
        dataPFC.setPotassium(potassium_text.getText().toString());

        return dataPFC;
    }

    private void AuditBarCode() {
        collRef.whereEqualTo("bar_code", LocalDataPFC.getBar_code())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        QuerySnapshot documentSnapshot = task.getResult();
                        if (!documentSnapshot.isEmpty()){
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                DataPFC dataPFC = queryDocumentSnapshot.toObject(DataPFC.class);
                                WriteListFromFS(dataPFC);
                            }
                        }
                        else{
                            WriteTextList();
                        }
                    }
                });
    }

    private void WriteListFromFS(DataPFC dataPFC) {
        potassium_text.setText(dataPFC.getPotassium());
        salt_text.setText(dataPFC.getSalt());
        calcium_text.setText(dataPFC.getCalcium());
        cellulose_text.setText(dataPFC.getCellulose());
        watter_text.setText(dataPFC.getWatter());
        casein_protein_text.setText(dataPFC.getCasein_protein());
        agg_protein_text.setText(dataPFC.getAgg_protein());
        soy_protein_text.setText(dataPFC.getSoy_protein());
        whey_protein_text.setText(dataPFC.getWhey_protein());
        protein_text.setText(dataPFC.getProtein());
        complex_carbohydrate_text.setText(dataPFC.getComplex_carbohydrate());
        simple_carbohydrate_text.setText(dataPFC.getSimple_carbohydrates());
        carbohydrate_text.setText(dataPFC.getCarbohydrate());
        epa_text.setText(dataPFC.getEpa());
        dha_text.setText(dataPFC.getDha());
        ala_text.setText(dataPFC.getAla());
        omega3_text.setText(dataPFC.getOmega_3());
        omega6_text.setText(dataPFC.getOmega_6());
        omega9_text.setText(dataPFC.getOmega_9());
        trans_fat_text.setText(dataPFC.getTrans_fat());
        saturated_fat_text.setText(dataPFC.getSaturated_fat());
        fat_text.setText(dataPFC.getFat());
        calorie_text.setText(dataPFC.getCalorie());
        portion_text.setText(dataPFC.getPortion());
        name_text.setText(dataPFC.getName());
    }

    private void WriteTextList() {
        potassium_text.setText(LocalDataPFC.getPotassium());
        salt_text.setText(LocalDataPFC.getSalt());
        calcium_text.setText(LocalDataPFC.getCalcium());
        cellulose_text.setText(LocalDataPFC.getCellulose());
        watter_text.setText(LocalDataPFC.getWatter());
        casein_protein_text.setText(LocalDataPFC.getCasein_protein());
        agg_protein_text.setText(LocalDataPFC.getAgg_protein());
        soy_protein_text.setText(LocalDataPFC.getSoy_protein());
        whey_protein_text.setText(LocalDataPFC.getWhey_protein());
        protein_text.setText(LocalDataPFC.getProtein());
        complex_carbohydrate_text.setText(LocalDataPFC.getComplex_carbohydrate());
        simple_carbohydrate_text.setText(LocalDataPFC.getSimple_carbohydrates());
        carbohydrate_text.setText(LocalDataPFC.getCarbohydrate());
        epa_text.setText(LocalDataPFC.getEpa());
        dha_text.setText(LocalDataPFC.getDha());
        ala_text.setText(LocalDataPFC.getAla());
        omega3_text.setText(LocalDataPFC.getOmega_3());
        omega6_text.setText(LocalDataPFC.getOmega_6());
        omega9_text.setText(LocalDataPFC.getOmega_9());
        trans_fat_text.setText(LocalDataPFC.getTrans_fat());
        saturated_fat_text.setText(LocalDataPFC.getSaturated_fat());
        fat_text.setText(LocalDataPFC.getFat());
        calorie_text.setText(LocalDataPFC.getCalorie());
        portion_text.setText(LocalDataPFC.getPortion());
        name_text.setText(LocalDataPFC.getName());
    }

    private void WritList() {

        potassium.setText(LocalDataPFC.getPotassium());
        salt.setText(LocalDataPFC.getSalt());
        calcium.setText(LocalDataPFC.getCalcium());
        cellulose.setText(LocalDataPFC.getCellulose());
        watter.setText(LocalDataPFC.getWatter());
        casein_protein.setText(LocalDataPFC.getCasein_protein());
        agg_protein.setText(LocalDataPFC.getAgg_protein());
        soy_protein.setText(LocalDataPFC.getSoy_protein());
        whey_protein.setText(LocalDataPFC.getWhey_protein());
        protein.setText(LocalDataPFC.getProtein());
        complex_carbohydrate.setText(LocalDataPFC.getComplex_carbohydrate());
        simple_carbohydrate.setText(LocalDataPFC.getSimple_carbohydrates());
        carbohydrate.setText(LocalDataPFC.getCarbohydrate());
        epa.setText(LocalDataPFC.getEpa());
        dha.setText(LocalDataPFC.getDha());
        ala.setText(LocalDataPFC.getAla());
        omega3.setText(LocalDataPFC.getOmega_3());
        omega6.setText(LocalDataPFC.getOmega_6());
        omega9.setText(LocalDataPFC.getOmega_9());
        trans_fat.setText(LocalDataPFC.getTrans_fat());
        saturated_fat.setText(LocalDataPFC.getSaturated_fat());
        fat.setText(LocalDataPFC.getFat());
        calorie.setText(LocalDataPFC.getCalorie());
        portion.setText(LocalDataPFC.getPortion());
        name.setText(LocalDataPFC.getName());

    }

    private void FindView(View root) {
        save = root.findViewById(R.id.save_moder);

        portion = root.findViewById(R.id.portion);
        potassium = root.findViewById(R.id.potassium);
        salt = root.findViewById(R.id.salt);
        calcium = root.findViewById(R.id.calcium);
        cellulose = root.findViewById(R.id.cellulose);
        watter = root.findViewById(R.id.watter);
        casein_protein = root.findViewById(R.id.casein_protein);
        agg_protein = root.findViewById(R.id.agg_protein);
        soy_protein = root.findViewById(R.id.soy_protein);
        whey_protein = root.findViewById(R.id.whey_protein);
        protein = root.findViewById(R.id.protein);
        complex_carbohydrate = root.findViewById(R.id.complex_carbohydrate);
        simple_carbohydrate = root.findViewById(R.id.simple_carbohydrates);
        carbohydrate = root.findViewById(R.id.carbohydrate);
        epa = root.findViewById(R.id.epa);
        dha = root.findViewById(R.id.dha);
        ala = root.findViewById(R.id.ala);
        omega3 = root.findViewById(R.id.omega3);
        omega6 = root.findViewById(R.id.omega6);
        omega9 = root.findViewById(R.id.omega9);
        trans_fat = root.findViewById(R.id.trans_fat);
        saturated_fat = root.findViewById(R.id.saturated_fat);
        fat = root.findViewById(R.id.fat);
        calorie = root.findViewById(R.id.calorie);
        name = root.findViewById(R.id.name);

        name_text = root.findViewById(R.id.name_text);
        portion_text = root.findViewById(R.id.portion_text);
        potassium_text = root.findViewById(R.id.potassium_text);
        salt_text = root.findViewById(R.id.salt_text);
        calcium_text = root.findViewById(R.id.calcium_text);
        cellulose_text = root.findViewById(R.id.cellulose_text);
        watter_text = root.findViewById(R.id.watter_text);
        casein_protein_text = root.findViewById(R.id.casein_protein_text);
        agg_protein_text = root.findViewById(R.id.agg_protein_text);
        soy_protein_text = root.findViewById(R.id.soy_protein_text);
        whey_protein_text = root.findViewById(R.id.whey_protein_text);
        protein_text = root.findViewById(R.id.protein_text);
        complex_carbohydrate_text = root.findViewById(R.id.complex_carbohydrate_text);
        simple_carbohydrate_text = root.findViewById(R.id.simple_carbohydrates_text);
        carbohydrate_text = root.findViewById(R.id.carbohydrate_text);
        epa_text = root.findViewById(R.id.epa_text);
        dha_text = root.findViewById(R.id.dha_text);
        ala_text = root.findViewById(R.id.ala_text);
        omega3_text = root.findViewById(R.id.omega3_text);
        omega6_text = root.findViewById(R.id.omega6_text);
        omega9_text = root.findViewById(R.id.omega9_text);
        trans_fat_text = root.findViewById(R.id.trans_fat_text);
        saturated_fat_text = root.findViewById(R.id.saturated_fat_text);
        fat_text = root.findViewById(R.id.fat_text);
        calorie_text = root.findViewById(R.id.calorie_text);
    }
}