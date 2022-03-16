package com.sharkit.nextmonday.trash;

import com.sharkit.nextmonday.configuration.annotation.Query;
import com.sharkit.nextmonday.configuration.annotation.realisation.SQLiteRepository;

import java.util.List;
import java.util.Optional;

public interface PRepo extends SQLiteRepository<Person> {

    @Query("name = '%s'")
    Optional<List<Person>> findByDate(String date);

}
