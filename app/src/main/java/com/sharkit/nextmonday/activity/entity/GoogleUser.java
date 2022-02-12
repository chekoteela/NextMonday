package com.sharkit.nextmonday.activity.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleUser {
    private String id;
    private String email;
    private String name;
}
