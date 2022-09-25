package com.sharkit.nextmonday.main_menu.calculator.fragmant;

import static com.sharkit.nextmonday.main_menu.calculator.configuration.CalculatorBundleTag.CALCULATOR_CALENDAR_INFO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.adapter.RationAdapter;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorWidget;
import com.sharkit.nextmonday.main_menu.calculator.dialog.CreateMealTemplateDialog;
import com.sharkit.nextmonday.main_menu.calculator.domain.CalculatorCalendarInfo;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final CalculatorCalendarInfo calendarInfo = (CalculatorCalendarInfo) Optional.ofNullable(this.getArguments())
                .map(arg -> arg.getSerializable(CALCULATOR_CALENDAR_INFO))
                .orElse(CalculatorCalendarInfo.getDefault());

        final View view = inflater.inflate(R.layout.calculator_ration, container, false);
        final CalculatorWidget.RationWidget widget = CalculatorWidget.getInstance(view).getRationWidget();
        final List<Ration> rations = new ArrayList<>();
        final RationAdapter rationAdapter = new RationAdapter(this.getContext(), rations);

        calendarInfo.getCalculatorCalendarType().findRation(calendarInfo.getDate(), this.getContext())
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    queryDocumentSnapshots.forEach(queryDocumentSnapshot -> rations.add(queryDocumentSnapshot.toObject(Ration.class)));
                    widget.getRationList().setAdapter(rationAdapter);
                });

        widget.getCreate().setOnClickListener(v -> new CreateMealTemplateDialog(rationAdapter).show());
        return view;
    }

}
