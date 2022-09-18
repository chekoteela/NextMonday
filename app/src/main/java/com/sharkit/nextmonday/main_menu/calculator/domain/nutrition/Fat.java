package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Fat implements Serializable {

    private float generalFatWeight;
    private float saturatedFat;
    private float transFat;
    private float omega6;
    private float omega9;
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

    public Float getSum() {
        this.generalFatWeight = this.sum(this.saturatedFat, this.transFat, this.omega9, this.omega6, this.omega3.getOmega3());
        return this.generalFatWeight;
    }

    private Float sum(final Float... values) {
        return Arrays.stream(values)
                .reduce(0.0f, Float::sum);
    }
}
