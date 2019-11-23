package com.faizan.revolut.exceptions;

import org.junit.Assert;
import org.junit.Test;

public class InvalidPartyExceptionTest {

    @Test
    public void json() {
        Assert.assertEquals(new InvalidPartyException().json(), "{ \"status\" : \"Invalid party id provided\" }");
    }

    @Test
    public void status() {
        Assert.assertEquals(new InvalidPartyException().status(), 400);
    }
}
