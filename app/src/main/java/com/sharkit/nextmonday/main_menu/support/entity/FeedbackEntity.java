package com.sharkit.nextmonday.main_menu.support.entity;

import com.sharkit.nextmonday.main_menu.support.entity.enums.FeedbackStatus;
import com.sharkit.nextmonday.main_menu.support.entity.enums.FeedbackType;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {

    private String id;
    private String userId;
    private String tittle;
    private ArrayList<FeedbackMessage> messages;
    private FeedbackStatus feedbackStatus;
    private FeedbackType feedbackType;
}
