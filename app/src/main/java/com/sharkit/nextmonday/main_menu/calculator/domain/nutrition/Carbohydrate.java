package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Carbohydrate {

    private float generalCarbohydrateWeight;
    private Float simpleCarbohydrate;
    private Float complexCarbohydrate;

    public void calculateByPortion(final Integer portion) {
        final float coefficient  = 100f / portion;

        this.generalCarbohydrateWeight = coefficient * this.generalCarbohydrateWeight;
        this.simpleCarbohydrate = coefficient * this.simpleCarbohydrate;
        this.complexCarbohydrate = coefficient * this.complexCarbohydrate;
    }
}
