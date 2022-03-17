package com.sharkit.nextmonday.configuration.annotation.realisation;

import java.util.Optional;

public interface SQLiteRepository<T> {

    void create(T target);
    boolean delete(String id);
    boolean update(T entity, String id);
    Optional<T> findById(String id);

}
