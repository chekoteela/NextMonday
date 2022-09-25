package com.sharkit.nextmonday.main_menu.calculator.service.calendar_type;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.main_menu.calculator.db.firebase.RationRepository;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;
import com.sharkit.nextmonday.main_menu.calculator.enums.action.CalculatorCalendarTypeAction;

public class PresentCalendarService implements CalculatorCalendarTypeAction {


    @Override
    public Task<QuerySnapshot> findRation(final String date, final Context context) {
        final RationRepository repository = RationRepository.getInstance(context);

        this.createMealsByTemplates(date, context, repository);

        return repository.findByDate(date);
    }

    private void createMealsByTemplates(final String date, final Context context, final RationRepository repository) {

        repository.findTemplates()
                .addOnSuccessListener(queryDocumentSnapshots -> repository.createIfNotExist(queryDocumentSnapshots.toObjects(Ration.class), date));
    }
}
