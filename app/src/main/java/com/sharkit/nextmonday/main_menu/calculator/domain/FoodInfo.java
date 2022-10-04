package com.sharkit.nextmonday.main_menu.calculator.domain;

import com.sharkit.nextmonday.main_menu.calculator.domain.nutrition.FoodNutrition;
import com.sharkit.nextmonday.main_menu.calculator.enums.FoodStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class FoodInfo extends FoodNutrition implements Serializable {

    private String id;
    private String name;
    private Integer portion;
    private FoodStatus foodStatus;
    private List<String> tag;

    public void calculateByPortion() {
        Optional.ofNullable(this.portion)
                .filter(value -> !value.equals(100))
                .ifPresent(this::calculateByPortion);
    }

    public void generateKey() {
        String inputString = this.name.toLowerCase();
        this.tag = new ArrayList<>();
        final String[] tagArray = inputString.split(" ");

        for (final String word : tagArray) {
            String a = "";
            final char[] b = inputString.toCharArray();

            for (final char c : b) {
                a += c;
                this.tag.add(a);
            }
            inputString = inputString.replace(word, "").trim();
        }
    }
}
