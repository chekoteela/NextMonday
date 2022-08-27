package com.sharkit.nextmonday.main_menu.support.widget;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportWidget {

    private final View view;

    public FeedbackWidget getFeedbackWidget() {
        return new FeedbackWidget();
    }

    public static SupportWidget newInstance(View view) {
        return new SupportWidget(view);
    }

    @Getter
    public class FeedbackWidget {
        private final Spinner typeOfFeedback;
        private final EditText textOfFeedback;
        private final ListView messageList;
        private final Button sendFeedback;

        public FeedbackWidget() {
            this.typeOfFeedback = view.findViewById(R.id.feedback_spinner_id);
            this.textOfFeedback = view.findViewById(R.id.feedback_text_id);
            this.messageList = view.findViewById(R.id.message_list_id);
            this.sendFeedback = view.findViewById(R.id.send_feedback_id);
        }
    }

}
