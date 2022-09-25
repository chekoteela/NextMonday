package com.sharkit.nextmonday.main_menu.calculator.db.firebase;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.main_menu.calculator.configuration.navigation.CalculatorNavigation;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;

import java.util.List;

public class RationRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Context context;
    private final String path;

    public static RationRepository getInstance(final Context context) {
        return new RationRepository(context);
    }

    private RationRepository(final Context context) {
        final String userId = new UserSharedPreference(context).get().getId();

        this.context = context;
        this.path = context.getString(R.string.path_to_ration).replace(context.getString(R.string.path_variable_user_id), userId);
    }

    public void createTemplate(final Ration ration) {

        final String templatePath = this.path.replace(this.context.getString(R.string.path_variable_type_of_ration), this.context.getString(R.string.path_replacement_type_of_ration));

        this.db.collection(templatePath)
                .document(ration.getId())
                .set(ration);
    }

    public Task<QuerySnapshot> findByDate(final String date) {

        final String pathByDate = this.path.replace(this.context.getString(R.string.path_variable_type_of_ration), date);

        return this.db.collection(pathByDate)
                .get();
    }

    public void createIfNotExist(final List<Ration> rations, final String date) {

        final String pathByDate = this.path.replace(this.context.getString(R.string.path_variable_type_of_ration), date);

        this.findByDate(date)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        rations.forEach(ration -> RationRepository.this.db.collection(pathByDate)
                                .document(ration.getId())
                                .set(ration));
                        CalculatorNavigation.getInstance(this.context).moveToRationMenu();
                    }
                });
    }

    public Task<QuerySnapshot> findTemplates() {

        final String templatePath = this.path.replace(this.context.getString(R.string.path_variable_type_of_ration), this.context.getString(R.string.path_replacement_type_of_ration));

        return this.db.collection(templatePath)
                .get();
    }
}
