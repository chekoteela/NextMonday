package com.sharkit.nextmonday.main_menu.calculator.domain;

import com.sharkit.nextmonday.main_menu.calculator.enums.CalculatorCalendarType;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CalculatorCalendarInfo implements Serializable {

    private String date;
    private CalculatorCalendarType calculatorCalendarType;

    public static CalculatorCalendarInfo getDefault() {

        return CalculatorCalendarInfo.builder()
                .calculatorCalendarType(CalculatorCalendarType.PRESENT)
                .date(DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime()))
                .build();
    }
}
