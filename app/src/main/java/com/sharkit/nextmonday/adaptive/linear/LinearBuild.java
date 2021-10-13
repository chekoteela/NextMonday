package com.sharkit.nextmonday.adaptive.linear;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearBuild implements LinearMethod {
    private EditText editText;
    private TextView textView;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params;

    public LinearBuild(Context context) {
        HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        WIDTH = context.getResources().getDisplayMetrics().widthPixels;
    }

    private final int HEIGHT;
    private final int WIDTH;

    /* Set custom params for your widget, heightCoefficient and widthCoefficient is a coefficient
     that determines the ratio of the size of your widget to the size of the screen in pixels*/

    /*setParam give your widget a width and height depending on the coefficient*/
    @Override
    public void setParam(float heightCoefficient, float widthCoefficient) {
        params = new LinearLayout.LayoutParams((int) (HEIGHT / heightCoefficient), (int) (WIDTH / widthCoefficient));
        getWidget().setLayoutParams(params);
    }

    @Override
    public void setParam(int heightCoefficient, int widthCoefficient) {
        params = new LinearLayout.LayoutParams(HEIGHT / heightCoefficient, WIDTH / widthCoefficient);
        getWidget().setLayoutParams(params);
    }

    @Override
    public LinearLayout.LayoutParams getParams() {
        return params;
    }

    @Override
    public void setMargin(float heightCoefficient, float widthCoefficient) {

    }

    @Override
    public void setMargin(int heightCoefficient, int widthCoefficient) {

    }

    @Override
    public View getWidget() {
        if (editText != null) {
            return editText;
        }else if (textView != null){
            return textView;
        }else if (linearLayout != null){
            return linearLayout;
        }
        return null;
    }

    @Override
    public void setWidget(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void setWidget(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void setWidget(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}
