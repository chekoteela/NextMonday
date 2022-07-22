package com.sharkit.nextmonday.configuration.widget_finder;

import android.annotation.SuppressLint;
import android.view.View;

import com.sharkit.nextmonday.configuration.widget_finder.layout.AuthorisationMenuWidget;
import com.sharkit.nextmonday.configuration.widget_finder.layout.RegisterMenuWidget;

import lombok.Getter;

@SuppressLint("StaticFieldLeak")
@Getter
public final class WidgetContainer {

    private final View view;
    private final RegisterMenuWidget registerMenuWidget;
    private final AuthorisationMenuWidget authorisationMenuWidget;

    private WidgetContainer(View view) {
        this.view = view;
        registerMenuWidget = new RegisterMenuWidget(view);
        authorisationMenuWidget = new AuthorisationMenuWidget(view);
    }

    public static WidgetContainer newInstance(View view) {
        return new WidgetContainer(view);
    }

}
