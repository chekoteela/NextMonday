package com.sharkit.nextmonday.configuration.adaptive.widget_adaptive.linear;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.WidgetSize;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions.Dimmension;
import com.sharkit.nextmonday.configuration.adaptive.layout.LinearLayoutAdaptive;

public class ButtonLinearAdaptive implements LinearLayoutAdaptive {

    private final Button button;
    private LinearLayout.LayoutParams params;
    private WidgetSize size;
    private Context context;
    private int HEIGHT;
    private int WIDTH;

    private ButtonLinearAdaptive(Button button) {
        this.button = button;
    }

    public static ButtonLinearAdaptive setButton(Button button) {
        return new ButtonLinearAdaptive(button);
    }

    @Override
    public LinearLayoutAdaptive setTemplateContext(TemplateAdaptive template, Context context) {
        this.context = context;
        size = template.getWidgetSize(Dimmension.BUTTON).setContext(context);
        return this;
    }

    @Override
    public LinearLayoutAdaptive setContext(Context context) {
        this.context = context;
        HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        return this;
    }

    @Override
    public LinearLayoutAdaptive setParams() {
        params = new LinearLayout.LayoutParams(size.getWidth(), size.getHeight());
        return this;
    }

    @Override
    public LinearLayoutAdaptive setParams(float widthCoefficient, float heightCoefficient) {
        params = new LinearLayout.LayoutParams((int) (WIDTH / widthCoefficient), (int) (HEIGHT / heightCoefficient));
        return this;
    }

    @Override
    public LinearLayoutAdaptive setMargins(float leftCoefficient, float topCoefficient, float rightCoefficient, float bottomCoefficient) {
        params.setMargins((int) (WIDTH / leftCoefficient), (int) (topCoefficient / HEIGHT),
                (int) (WIDTH / rightCoefficient), (int) (HEIGHT / bottomCoefficient));
        return this;
    }

    @Override
    public LinearLayoutAdaptive setMargins() {
        throwIfNull();
        params.setMargins(size.getMarginLeft(), size.getMarginTop(), size.getMarginRight(), size.getMarginBottom());
        return this;
    }

    @Override
    public LinearLayoutAdaptive setLayoutGravity(int gravity) {
        throwIfNull();
        params.gravity = gravity;
        return this;
    }

    @Override
    public void build() {
        throwIfNull();
        button.setLayoutParams(params);
    }

    private void throwIfNull() {
        if (context == null) {
            throw new NullPointerException("For the first you must to set context or template context");
        }
        if (params == null) {
            throw new NullPointerException("For the first you must to set params");
        }
    }

}
