package com.sharkit.nextmonday.main_menu.calculator.domain.nutrition;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Protein {

    private float generalProteinWeight;
    private float wheyProtein;
    private float soyProtein;
    private float aggProtein;
    private float caseinProtein;

    public void calculateByPortion(final Integer portion) {
        final float coefficient  = 100f / portion;

        this.generalProteinWeight = coefficient * this.generalProteinWeight;
        this.wheyProtein = coefficient * this.wheyProtein;
        this.soyProtein = coefficient * this.soyProtein;
        this.aggProtein = coefficient * this.aggProtein;
        this.caseinProtein = coefficient * this.caseinProtein;
    }

}
