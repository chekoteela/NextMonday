package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.adapter.RationAdapter;
import com.sharkit.nextmonday.main_menu.calculator.configuration.navigation.CalculatorNavigation;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.db.firebase.RationRepository;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;
import com.sharkit.nextmonday.main_menu.calculator.enums.RationNotification;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class RationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calculator_ration, container, false);
        final CalculatorWidget.RationWidget widget = CalculatorWidget.getInstance(view).getRationWidget();
        final RationRepository repository = RationRepository.getInstance(this.getContext());
        final String date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());

        final Ration ration = Ration.builder()
                .id(UUID.randomUUID().toString())
                .name("Lunch")
                .notification(RationNotification.NOTIFICATION)
                .date(date)
                .build();

        widget.getCreate().setOnClickListener(v -> repository.create(ration));
        repository.findByDate().addOnSuccessListener(queryDocumentSnapshots ->
                widget.getRationList().setAdapter(new RationAdapter(RationFragment.this.getContext(), queryDocumentSnapshots.toObjects(Ration.class))));
        return view;
    }
}
