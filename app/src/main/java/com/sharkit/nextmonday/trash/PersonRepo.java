package com.sharkit.nextmonday.trash;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sharkit.nextmonday.configuration.annotation.Query;
import com.sharkit.nextmonday.configuration.annotation.realisation.SQLiteTemplate;
import com.sharkit.nextmonday.diary.db.sqlite.DiaryTaskRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressLint("NewApi")
public class PersonRepo extends SQLiteTemplate<Person> implements PRepo {

    private PersonRepo(Context context) {
        super(context, Person.class);
        onCreate(super.getReadableDatabase());
    }

    public static PersonRepo getInstance(Context context){
        return new PersonRepo(context);
    }

    @Override
    public Optional<List<Person>> findByDate(String date) {
        return find(PRepo.class, Objects.requireNonNull(new Object() {}
                .getClass().getEnclosingMethod()).getName(), date);
    }

}
