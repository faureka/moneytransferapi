package com.faizan.revolut.models;

import com.faizan.revolut.interfaces.Party;

public class Person implements Party {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
