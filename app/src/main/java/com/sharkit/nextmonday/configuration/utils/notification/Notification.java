package com.sharkit.nextmonday.configuration.utils.notification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification {
    public static final String CHANNEL_1 = "Ежидневник";
    public static final String UNCOMPLETED_TASK = "У вас есть невыполненные задачи";
    public static final String SUMMARY_TEXT= "part of the Next Monday";
    public static final String CONTENT_TITLE= "text of target";
    public static final String BIG_TEXT = "description";
    public static final String DIARY_TASK_ID = "diary_task_id";
    public static final String DIARY_TASK_TIME = "diary_task_time";
}
