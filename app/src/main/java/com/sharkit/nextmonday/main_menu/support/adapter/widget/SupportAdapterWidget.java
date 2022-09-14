package com.sharkit.nextmonday.main_menu.support.adapter.widget;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharkit.nextmonday.R;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupportAdapterWidget {

    private final View view;

    public MainFeedbackAdapterWidget getMainFeedbackAdapterWidget() {
        return new MainFeedbackAdapterWidget();
    }

    public FeedbackMessageWidget getFeedbackMessageWidget() {
        return new FeedbackMessageWidget();
    }

    public static SupportAdapterWidget newInstance(final View view) {
        return new SupportAdapterWidget(view);
    }

    @Getter
    public class MainFeedbackAdapterWidget {
        private final TextView text;
        private final LinearLayout parentItem;

        public MainFeedbackAdapterWidget() {
            this.text = SupportAdapterWidget.this.view.findViewById(R.id.creation_date);
            this.parentItem = SupportAdapterWidget.this.view.findViewById(R.id.parent_item_id);
        }
    }

    @Getter
    public class FeedbackMessageWidget {
        private final TextView messageText;

        public FeedbackMessageWidget() {
            this.messageText = SupportAdapterWidget.this.view.findViewById(R.id.message_text);
        }
    }

}
