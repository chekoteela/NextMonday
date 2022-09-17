package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;

import java.util.Arrays;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Omega3 {

    private float omega3;
    private float ala;
    private float epa;
    private float dha;

    public void calculateProteinByPortion(final Integer portion) {
        final float coefficient = 100f / portion;

        this.ala = coefficient * this.ala;
        this.epa = coefficient * this.epa;
        this.dha = coefficient * this.dha;
    }

    public Float getSum() {
        return this.sum(this.ala, this.epa, this.dha);
    }

    private Float sum(final Float... values) {
        return Arrays.stream(values)
                .reduce(0.0f, Float::sum);
    }
}
