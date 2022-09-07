package com.sharkit.nextmonday.main_menu.support.fragment;

import static com.sharkit.nextmonday.main_menu.support.configuration.BundleTag.FEEDBACK_ID;
import static com.sharkit.nextmonday.main_menu.support.configuration.BundleTag.LIST_OF_FEEDBACK_MESSAGES;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.auth.entity.User;
import com.sharkit.nextmonday.configuration.utils.service.UserSharedPreference;
import com.sharkit.nextmonday.main_menu.support.adapter.FeedbackMessageAdapter;
import com.sharkit.nextmonday.main_menu.support.db.FeedbackRepository;
import com.sharkit.nextmonday.main_menu.support.entity.FeedbackMessage;
import com.sharkit.nextmonday.main_menu.support.widget.SupportWidget;

import java.util.List;
import java.util.UUID;

public class FeedbackMessengerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.support_feedback_messanger, container, false);

        final String feedbackId = requireArguments().getString(FEEDBACK_ID);
        final List<FeedbackMessage> messages = requireArguments().getParcelableArrayList(LIST_OF_FEEDBACK_MESSAGES);

        final SupportWidget.FeedbackMessengerWidget widget = SupportWidget.newInstance(view).getFeedbackMessengerWidget();
        final FeedbackMessageAdapter adapter = new FeedbackMessageAdapter(messages, getContext());

        widget.getMessageList().setAdapter(adapter);
        widget.getSendFeedback().setOnClickListener(v -> sendMessage(widget, adapter, messages, feedbackId));
        widget.getCloseFeedback().setOnClickListener(v -> closeFeedback(feedbackId));

        return view;
    }

    private void closeFeedback(String feedbackId) {
        final FeedbackRepository repository = FeedbackRepository.getInstance(getContext());
        repository.closeFeedback(feedbackId);
    }

    private void sendMessage(SupportWidget.FeedbackMessengerWidget widget, FeedbackMessageAdapter adapter, List<FeedbackMessage> messages, String feedbackId) {
        final User user = new UserSharedPreference(requireContext()).get();
        final FeedbackRepository repository = FeedbackRepository.getInstance(getContext());

        FeedbackMessage message = FeedbackMessage.builder()
                .id(UUID.randomUUID().toString())
                .message(widget.getTextOfFeedback().getText().toString())
                .messageType(user.getRole().getFeedbackMessageType())
                .build();

        messages.add(message);
        repository.updateMessage(feedbackId, messages);
        adapter.notifyDataSetChanged();
    }
}
