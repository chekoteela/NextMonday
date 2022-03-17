package com.sharkit.nextmonday.trash;

import com.sharkit.nextmonday.configuration.annotation.Collection;
import com.sharkit.nextmonday.configuration.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Collection(collection = "person")
public class Person {

    @Id
    private String id;
    private String name;
    private String userId;
}
