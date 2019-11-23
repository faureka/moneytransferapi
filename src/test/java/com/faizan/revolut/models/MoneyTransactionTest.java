package com.faizan.revolut.models;

import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.fixtures.AccountDetailsFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public class MoneyTransactionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private AccountDetails debitAccount;
    private AccountDetails creditAccount;

    @Before
    public void setup() {
        AccountDetailsFixture fixture = new AccountDetailsFixture();
        debitAccount = fixture.getDebit();
        creditAccount = fixture.getCredit();
    }

    @After
    public void tearDown() {
        thrown = ExpectedException.none();
    }

    @Test
    public void constructorWithAllNulls() throws InvalidTransactionException {
        thrown.expect(InvalidTransactionException.class);
        MoneyTransaction.create(null, null, null, null);
    }

    @Test
    public void constructorWithIdNull() throws InvalidTransactionException {
        thrown.expect(InvalidTransactionException.class);
        MoneyTransaction.create(null, debitAccount, creditAccount, BigDecimal.ONE);
    }

    @Test
    public void constructorWithDebitNull() throws InvalidTransactionException {
        thrown.expect(InvalidTransactionException.class);
        MoneyTransaction.create(1L, null, creditAccount, BigDecimal.ONE);
    }

    @Test
    public void constructorWithCreditNull() throws InvalidTransactionException {
        thrown.expect(InvalidTransactionException.class);
        MoneyTransaction.create(1L, debitAccount, null, BigDecimal.ONE);
    }

    @Test
    public void constructorWithAmountNull() throws InvalidTransactionException {
        thrown.expect(InvalidTransactionException.class);
        MoneyTransaction.create(1L, debitAccount, creditAccount, null);
    }

    @Test
    public void invalidAmountNegative() throws InvalidTransactionException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Amount must be greater than zero");
        MoneyTransaction.create(1L, debitAccount, creditAccount, BigDecimal.valueOf(-1));
    }

    @Test
    public void invalidAmountZero() throws InvalidTransactionException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Amount must be greater than zero");
        MoneyTransaction.create(1L, debitAccount, creditAccount, BigDecimal.valueOf(0));
    }

    @Test
    public void invalidAccountDetails() throws InvalidTransactionException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Accounts must be different");
        MoneyTransaction.create(1L, debitAccount, debitAccount, BigDecimal.ONE);
    }
}
