package com.sharkit.nextmonday.main_menu.calculator.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.calculator.adapter.RationAdapter;
import com.sharkit.nextmonday.main_menu.calculator.configuration.widget.CalculatorDialogWidget;
import com.sharkit.nextmonday.main_menu.calculator.db.firebase.RationRepository;
import com.sharkit.nextmonday.main_menu.calculator.domain.Ration;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateMealTemplateDialog {

    private final RationAdapter rationAdapter;

    public void show() {
        final AlertDialog dialog = new AlertDialog.Builder(this.rationAdapter.getContext()).create();
        final View view = LayoutInflater.from(this.rationAdapter.getContext()).inflate(R.layout.dialog_create_meal, null);
        final CalculatorDialogWidget.CreateMealDialogWidget widget = CalculatorDialogWidget.getInstance(view).getCreateMealDialogWidget();

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.rationAdapter.getContext().getString(R.string.button_create),
                (dialog1, which) -> {
                    final Ration ration = this.buildRation(widget.getEditText().getText().toString());
                    this.createRation(ration);
                    dialog1.dismiss();
                });

        dialog.setView(view);
        dialog.show();
    }

    private void createRation(final Ration ration) {
        final RationRepository repository = RationRepository.getInstance(this.rationAdapter.getContext());
        repository.createTemplate(ration);
        this.rationAdapter.getRations().add(ration);
        this.rationAdapter.notifyDataSetChanged();
    }

    private Ration buildRation(final String name) {
        return Ration.builder()
                .id(UUID.randomUUID().toString())
                .date(DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime()))
                .name(name)
                .build();
    }
}
