package com.sharkit.nextmonday.configuration.container;

import lombok.Getter;

@Getter
public final class AppContainer {

    private static final AppContainer container = new AppContainer();

    public static AppContainer getInstance() {
        return container;
    }

}
