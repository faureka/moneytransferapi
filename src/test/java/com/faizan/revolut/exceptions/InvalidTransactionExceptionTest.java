package com.faizan.revolut.exceptions;

import org.junit.Assert;
import org.junit.Test;

public class InvalidTransactionExceptionTest {

    @Test
    public void json() {
        Assert.assertEquals(new InvalidTransactionException().json(),
                "{ \"status\" : \"Invalid transaction id provided\" }");
    }

    @Test
    public void status() {
        Assert.assertEquals(new InvalidTransactionException().status(), 400);
    }
}
