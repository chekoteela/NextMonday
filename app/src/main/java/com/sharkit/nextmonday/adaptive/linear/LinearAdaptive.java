package com.sharkit.nextmonday.adaptive.linear;

import android.content.Context;
import android.widget.EditText;

public class LinearAdaptive implements LinearBuildMethod{
    private final LinearBuild linearBuild;

    public LinearAdaptive(Context context) {
        linearBuild = new LinearBuild(context);
    }

    @Override
    public LinearBuildMethod setParam(float heightCoefficient, float widthCoefficient) {
        linearBuild.setParam(heightCoefficient, widthCoefficient);
        return this;
    }

    @Override
    public LinearBuildMethod setParam(int heightCoefficient, int widthCoefficient) {
        linearBuild.setParam(heightCoefficient, widthCoefficient);
        return this;
    }

    @Override
    public LinearBuildMethod setWidget(EditText editText) {
        linearBuild.setWidget(editText);
        return this;
    }

    @Override
    public LinearBuild build() {
        return linearBuild;
    }
}
