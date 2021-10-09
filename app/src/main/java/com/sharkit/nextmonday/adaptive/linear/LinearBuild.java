package com.sharkit.nextmonday.adaptive.linear;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LinearBuild implements LinearMethod{
    private EditText editText;

    public LinearBuild (Context context){
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(HEIGHT/heightCoefficient),(int)(WIDTH/widthCoefficient));
        getWidget().setLayoutParams(params);
    }

    @Override
    public void setParam(int heightCoefficient, int widthCoefficient) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(HEIGHT/heightCoefficient,WIDTH/widthCoefficient);
        getWidget().setLayoutParams(params);
    }



    @Override
    public EditText getWidget() {
        return editText;
    }

    @Override
    public void setWidget(EditText editText) {
        this.editText = editText;
    }
}
