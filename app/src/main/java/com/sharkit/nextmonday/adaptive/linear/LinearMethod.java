package com.sharkit.nextmonday.adaptive.linear;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public interface LinearMethod {
    void setParam(float heightCoefficient, float widthCoefficient);
    void setParam(int heightCoefficient, int widthCoefficient);


    void setMargin(float heightCoefficient, float widthCoefficient);
    void setMargin(int heightCoefficient, int widthCoefficient);

    View getWidget();


    LinearLayout.LayoutParams getParams();
    void setWidget(EditText editText);
    void setWidget(TextView textView);
    void setWidget(LinearLayout linearLayout);

}
