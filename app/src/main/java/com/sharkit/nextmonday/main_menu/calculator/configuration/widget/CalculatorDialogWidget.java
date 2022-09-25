package com.sharkit.nextmonday.main_menu.calculator.configuration.widget;

import android.view.View;
import android.widget.EditText;

import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculatorDialogWidget {

    private final View view;

    public CreateMealDialogWidget getCreateMealDialogWidget() {
        return new CreateMealDialogWidget();
    }

    public static CalculatorDialogWidget getInstance(final View view) {
        return new CalculatorDialogWidget(view);
    }

    @Getter
    public class CreateMealDialogWidget {

        private final EditText editText;

        public CreateMealDialogWidget() {
            this.editText = CalculatorDialogWidget.this.view.findViewById(R.id.meal_name_id);
        }
    }
}
