package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;

import java.io.Serializable;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carbohydrate implements Serializable {

    private float generalCarbohydrateWeight;
    private float simpleCarbohydrate;
    private float complexCarbohydrate;

    public void calculateByPortion(final Integer portion) {
        final float coefficient  = 100f / portion;

        this.generalCarbohydrateWeight = coefficient * this.generalCarbohydrateWeight;
        this.simpleCarbohydrate = coefficient * this.simpleCarbohydrate;
        this.complexCarbohydrate = coefficient * this.complexCarbohydrate;
    }

    public Float getSum() {
        this.generalCarbohydrateWeight = this.sum(this.simpleCarbohydrate, this.complexCarbohydrate);
        return this.generalCarbohydrateWeight;
    }

    private Float sum(final Float... values) {
        return Arrays.stream(values)
                .reduce(0.0f, Float::sum);
    }
}
