package com.sharkit.nextmonday.main_menu.calculator.domain;

import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.FoodNutrition;
import com.sharkit.nextmonday.main_menu.calculator.enums.FoodStatus;

import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@SuperBuilder
public class FoodInfo extends FoodNutrition {

    private String name;
    private Integer portion;
    private FoodStatus foodStatus;

    public void calculateByPortion() {
        Optional.ofNullable(this.portion)
                .filter(value -> !value.equals(100))
                .ifPresent(this::calculateByPortion);
    }
}
