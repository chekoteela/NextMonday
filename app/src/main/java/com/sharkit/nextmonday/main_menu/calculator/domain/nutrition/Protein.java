package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;


import java.io.Serializable;
import java.util.Arrays;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Protein implements Serializable {

    private float generalProteinWeight;
    private float wheyProtein;
    private float soyProtein;
    private float aggProtein;
    private float caseinProtein;

    public void calculateByPortion(final Integer portion) {
        final float coefficient = 100f / portion;

        this.generalProteinWeight = coefficient * this.generalProteinWeight;
        this.wheyProtein = coefficient * this.wheyProtein;
        this.soyProtein = coefficient * this.soyProtein;
        this.aggProtein = coefficient * this.aggProtein;
        this.caseinProtein = coefficient * this.caseinProtein;
    }

    public Float getSum() {
        this.generalProteinWeight = this.sum(this.aggProtein, this.caseinProtein, this.soyProtein, this.wheyProtein);
        return this.generalProteinWeight;
    }

    private Float sum(final Float... values) {
        return Arrays.stream(values)
                .reduce(0.0f, Float::sum);
    }
}
