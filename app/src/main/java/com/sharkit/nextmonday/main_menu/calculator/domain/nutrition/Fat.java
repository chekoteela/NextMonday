package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Fat {

    private float generalFatWeight;
    private Float saturatedFat;
    private Float transFat;
    private Float omega6;
    private Float omega9;
    private Omega3 omega3;

    public void calculateByPortion(final Integer portion) {
        final float coefficient  = 100f / portion;

        this.generalFatWeight = coefficient * this.generalFatWeight;
        this.saturatedFat = coefficient * this.saturatedFat;
        this.transFat = coefficient * this.transFat;
        this.omega9 = coefficient * this.omega9;
        this.omega6 = coefficient * this.omega6;
        this.omega3.calculateProteinByPortion(portion);
    }
}
