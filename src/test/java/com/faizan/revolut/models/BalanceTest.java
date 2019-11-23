package com.faizan.revolut.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BalanceTest {

    private Balance balance;
    private Balance balanceEmpty;

    @Before
    public void setup() {
        balance = new Balance(BigDecimal.ONE);
        balanceEmpty = new Balance();
    }

    @Test
    public void validBalance() {
        Assert.assertNotNull(balance);
        Assert.assertNotNull(balance.getBalance());
        Assert.assertEquals(balance.getBalance(), BigDecimal.ONE);
    }

    @Test
    public void emptyConstructor() {
        Assert.assertNotNull(balanceEmpty);
        Assert.assertNull(balanceEmpty.getBalance());
    }

    @Test
    public void setBalance() {
        Assert.assertNotNull(balanceEmpty);
        Assert.assertNull(balanceEmpty.getBalance());
        balanceEmpty.setBalance(BigDecimal.ONE);
        Assert.assertEquals(balanceEmpty.getBalance(), BigDecimal.ONE);
    }
}
