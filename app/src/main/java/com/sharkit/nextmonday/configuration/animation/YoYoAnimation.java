package com.sharkit.nextmonday.configuration.animation;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class YoYoAnimation {

    private static final YoYoAnimation animation = new YoYoAnimation();

    public static YoYoAnimation getInstance() {
        return animation;
    }

    public void setRubberBandAnimation(final View view) {
        YoYo.with(Techniques.RubberBand)
                .duration(700)
                .repeat(1)
                .playOn(view);
    }
}
