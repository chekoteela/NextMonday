package com.sharkit.nextmonday.configuration.annotation.realisation;

import java.util.Optional;

public interface SQLiteRepository<T> {

    void create(T target);

    Optional<T> findById(String id);
}
