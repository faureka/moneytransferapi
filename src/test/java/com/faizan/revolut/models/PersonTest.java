package com.faizan.revolut.models;

import com.faizan.revolut.fixtures.PersonFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private PersonFixture personFixture;

    @Before
    public void setup() {
        personFixture = new PersonFixture();
    }

    @Test
    public void validCharInPersonName() {
        Assert.assertNotNull(personFixture.get());
        Assert.assertEquals("Revolut", personFixture.getName());
    }
}
