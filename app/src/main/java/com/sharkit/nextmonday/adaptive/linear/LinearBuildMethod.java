package com.sharkit.nextmonday.adaptive.linear;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public interface LinearBuildMethod {

    LinearBuildMethod setParam(float heightCoefficient, float widthCoefficient);
    LinearBuildMethod setParam(int heightCoefficient, int widthCoefficient);

    LinearBuildMethod setMargin(float heightCoefficient, float widthCoefficient);
    LinearBuildMethod setMargin(int heightCoefficient, int widthCoefficient);
    LinearLayout.LayoutParams getParams();

    LinearBuildMethod setWidget(EditText editText);
    LinearBuildMethod setWidget(TextView textView);
    LinearBuildMethod setWidget(LinearLayout linearLayout);
    LinearBuild build();
}
