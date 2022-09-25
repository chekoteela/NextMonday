package com.sharkit.nextmonday.main_menu.calculator.domain;

import com.sharkit.nextmonday.main_menu.calculator.enums.RationNotification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ration {

    private String id;
    private String name;
    private String description;
    private RationNotification notification;
    private String date;
}
