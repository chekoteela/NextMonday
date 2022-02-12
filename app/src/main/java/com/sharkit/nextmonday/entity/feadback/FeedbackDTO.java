package com.sharkit.nextmonday.entity.feadback;

import lombok.Data;

@Data
public class FeedbackDTO {
    private String id;
    private String name;
    private String email;
    private int reason;
    private String text;
    private long date;

}
