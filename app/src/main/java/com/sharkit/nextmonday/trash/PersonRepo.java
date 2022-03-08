package com.sharkit.nextmonday.trash;

import android.content.Context;

import com.sharkit.nextmonday.configuration.annotation.realisation.SQLiteTemplate;

import lombok.SneakyThrows;

public class PersonRepo extends SQLiteTemplate<Person> {

    public PersonRepo(Context context) {
        super(context, Person.class);
    }
}
