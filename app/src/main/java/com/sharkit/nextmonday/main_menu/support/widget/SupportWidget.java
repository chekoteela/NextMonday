package com.sharkit.nextmonday.main_menu.support.widget;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportWidget {

    private final View view;

    public FeedbackMessengerWidget getFeedbackMessengerWidget() {
        return new FeedbackMessengerWidget();
    }

    public FeedbackWidget getFeedbackWidget() {
        return new FeedbackWidget();
    }

    public static SupportWidget newInstance(View view) {
        return new SupportWidget(view);
    }

    @Getter
    public class FeedbackMessengerWidget {
        private final EditText textOfFeedback;
        private final ListView messageList;
        private final Button sendFeedback;
        private final Button closeFeedback;

        public FeedbackMessengerWidget() {
            this.textOfFeedback = view.findViewById(R.id.feedback_text_id);
            this.messageList = view.findViewById(R.id.message_list_id);
            this.sendFeedback = view.findViewById(R.id.send_feedback_id);
            this.closeFeedback = view.findViewById(R.id.close_feedback_id);
        }
    }

    @Getter
    public class FeedbackWidget {
        private final ListView listOfFeedback;
        private final Button create;

        public FeedbackWidget() {
            this.listOfFeedback = view.findViewById(R.id.list_of_feedback_id);
            this.create = view.findViewById(R.id.create_id);
        }
    }
}
