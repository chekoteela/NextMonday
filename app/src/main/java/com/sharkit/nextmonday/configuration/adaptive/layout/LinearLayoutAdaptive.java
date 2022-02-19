package com.sharkit.nextmonday.configuration.adaptive.layout;

import android.content.Context;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.TemplateAdaptive;

public interface LinearLayoutAdaptive {

    LinearLayoutAdaptive setTemplateContext(TemplateAdaptive template, Context context);
    LinearLayoutAdaptive setContext(Context context);
    LinearLayoutAdaptive setParams();
    LinearLayoutAdaptive setParams(float widthCoefficient, float heightCoefficient);
    LinearLayoutAdaptive setMargins(float leftCoefficient, float topCoefficient, float rightCoefficient, float bottomCoefficient);
    LinearLayoutAdaptive setMargins();
    LinearLayoutAdaptive setLayoutGravity(int gravity);

    void build();
}
