package com.faizan.revolut.fixtures;

import com.faizan.revolut.models.Person;

public class PersonFixture {

    private static final Person person = new Person("Revolut");

    public String getName() {
        return person.getName();
    }

    public Person get() {
        return person;
    }
}
