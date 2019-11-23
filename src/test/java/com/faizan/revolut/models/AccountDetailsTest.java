package com.faizan.revolut.models;

import com.faizan.revolut.fixtures.AccountDetailsFixture;
import com.faizan.revolut.interfaces.Party;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountDetailsTest {

    private Party holder;
    private AccountDetails accountDetails;
    private AccountDetailsFixture fixture;

    @Before
    public void setup() {
        holder = new Person("Revolut");
        fixture = new AccountDetailsFixture();
        accountDetails = AccountDetails.makeAccountWithDefaultBalance(315457352686L, holder);
    }

    @Test
    public void getNumber() {
        final AccountDetails a = accountDetails;
        Assert.assertNotNull(a);
        Assert.assertNotNull(a.getNumber());
        Assert.assertEquals(new Long(315457352686L), a.getNumber());
    }

    @Test
    public void getHolder() {
        final AccountDetails a = accountDetails;
        Assert.assertNotNull(a);
        Assert.assertNotNull(a.getHolder());
        Assert.assertEquals(a.getHolder(), holder);
    }

    @Test
    public void debitAndCredit() {
        final AccountDetails a = accountDetails;
        Assert.assertFalse(a.debit(BigDecimal.TEN));
        Assert.assertTrue(a.credit(BigDecimal.TEN));
        Assert.assertEquals(BigDecimal.TEN, a.getBalance());
        Assert.assertTrue(a.debit(BigDecimal.ONE));
        Assert.assertEquals(BigDecimal.valueOf(9), a.getBalance());
    }

    @Test
    public void checkLockIsNotSerialized() {
        final AccountDetails a = accountDetails;
        final String json = fixture.get();
        Assert.assertFalse(json.contains("lock"));
        Assert.assertEquals(json, new Gson().toJson(a));
    }
}
