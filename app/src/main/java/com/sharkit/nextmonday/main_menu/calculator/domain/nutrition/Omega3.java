package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Omega3 {

    private Float ala;
    private Float epa;
    private Float dha;

    public void calculateProteinByPortion(final Integer portion) {
        final float coefficient  = 100f / portion;

        this.ala = coefficient * this.ala;
        this.epa = coefficient * this.epa;
        this.dha = coefficient * this.dha;
    }
}
