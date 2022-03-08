package com.sharkit.nextmonday.configuration.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Collection {
    String collection();
}
