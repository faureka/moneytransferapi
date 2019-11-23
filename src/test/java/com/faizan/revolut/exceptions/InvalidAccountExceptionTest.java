package com.faizan.revolut.exceptions;

import org.junit.Assert;
import org.junit.Test;

public class InvalidAccountExceptionTest {

    @Test
    public void json() {
        Assert.assertEquals(new InvalidAccountException().json(),
                "{ \"status\" : \"Invalid account number provided\" }");
    }

    @Test
    public void status() {
        Assert.assertEquals(new InvalidAccountException().status(), 400);
    }
}
