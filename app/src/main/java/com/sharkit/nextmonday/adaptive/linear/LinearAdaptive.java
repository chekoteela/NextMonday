package com.sharkit.nextmonday.adaptive.linear;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    public LinearBuildMethod setMargin(float heightCoefficient, float widthCoefficient) {
        linearBuild.setMargin(heightCoefficient, widthCoefficient);
        return this;
    }

    @Override
    public LinearBuildMethod setMargin(int heightCoefficient, int widthCoefficient) {
        linearBuild.setMargin(heightCoefficient, widthCoefficient);
        return this;
    }

    @Override
    public LinearLayout.LayoutParams getParams() {
        return linearBuild.getParams();
    }

    @Override
    public LinearBuildMethod setWidget(EditText editText) {
        linearBuild.setWidget(editText);
        return this;
    }

    @Override
    public LinearBuildMethod setWidget(TextView textView) {
        linearBuild.setWidget(textView);
        return this;
    }

    @Override
    public LinearBuildMethod setWidget(LinearLayout linearLayout) {
        linearBuild.setWidget(linearLayout);
        return this;
    }


    @Override
    public LinearBuild build() {
        return linearBuild;
    }
}
