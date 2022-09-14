package com.sharkit.nextmonday.main_menu.support.dialog.widget;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportDialogWidget {

    private final View view;

    public NewFeedbackDialogWidget getNewFeedbackDialogWidget(){
        return new NewFeedbackDialogWidget();
    }

    public static SupportDialogWidget newInstance(final View view){
        return new SupportDialogWidget(view);
    }

    @Getter
    public class NewFeedbackDialogWidget {
        private final Spinner typeOfFeedback;
        private final EditText tittle;
        private final Button create;

        public NewFeedbackDialogWidget() {
            this.typeOfFeedback = SupportDialogWidget.this.view.findViewById(R.id.feedback_type_id);
            this.tittle = SupportDialogWidget.this.view.findViewById(R.id.tittle_id);
            this.create = SupportDialogWidget.this.view.findViewById(R.id.create_id);
        }
    }
}
