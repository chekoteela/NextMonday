package com.sharkit.nextmonday.main_menu.calculator.db.firebase;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RationRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String path;

    public static RationRepository getInstance(final Context context) {
        final String userId = new UserSharedPreference(context).get().getId();
        return new RationRepository(context.getString(R.string.path_to_ration).replace(context.getString(R.string.path_variable_user_id), userId));
    }

    public void create(final Ration ration) {

        this.db.collection(this.path)
                .document(ration.getId())
                .set(ration);
    }

    public Task<QuerySnapshot> findByDate() {
        return this.db.collection(this.path)
                .get();
    }
}
