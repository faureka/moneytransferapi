package com.faizan.revolut.webservices.rest;

import com.faizan.revolut.enums.TransactionState;
import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.fixtures.AccountDetailsFixture;
import com.faizan.revolut.interfaces.Transaction;
import com.faizan.revolut.models.MoneyTransaction;
import com.faizan.revolut.server.logic.TransactionLogicHelper;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TransactionLogicHelper.class)
public class TransactionHandlerTest {

    private TransactionHandler handler;

    @Before
    public void setup() throws InvalidTransactionException, InvalidAccountException {
        handler = new TransactionHandler();
        PowerMockito.mockStatic(TransactionLogicHelper.class);
        PowerMockito.when(TransactionLogicHelper.getAll()).thenReturn(Lists.newArrayList(MoneyTransaction
                .create(1L, AccountDetailsFixture.debit, AccountDetailsFixture.credit, BigDecimal.TEN)));
        PowerMockito.when(TransactionLogicHelper.getById(1L)).thenReturn(
                MoneyTransaction.create(1L, AccountDetailsFixture.debit, AccountDetailsFixture.credit, BigDecimal.TEN));
        PowerMockito.when(TransactionLogicHelper.getByAccountId(1L)).thenReturn(Lists.newArrayList(MoneyTransaction
                .create(1L, AccountDetailsFixture.debit, AccountDetailsFixture.credit, BigDecimal.TEN)));
        PowerMockito.when(TransactionLogicHelper.getById(0L)).thenThrow(InvalidTransactionException.class);
        PowerMockito.when(TransactionLogicHelper.getByAccountId(0L)).thenThrow(InvalidAccountException.class);
    }

    @Test
    public void getAll() {
        Response response = handler.getAll();
        Assert.assertEquals(response.getStatus(), 200);
        List<Transaction> transactions = (List<Transaction>) response.getEntity();
        Assert.assertEquals(transactions.get(0).getState(), TransactionState.NEW);
    }

    @Test
    public void getById() {
        Response response = handler.getById(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
        MoneyTransaction e = (MoneyTransaction) response.getEntity();
        Assert.assertEquals(e.getDebit().getNumber(), new Long(865930402721L));
    }

    @Test
    public void invalidGetById() {
        Response response = handler.getById(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 400);
        String e = (String) response.getEntity();
        boolean contains = e.contains("Invalid transaction id provided");
        Assert.assertEquals(contains, Boolean.TRUE);
    }

    @Test
    public void getByAccountId() {
        Response response = handler.getByAccountId(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
        List<Transaction> transactions = (List<Transaction>) response.getEntity();
        Assert.assertEquals(transactions.get(0).getDebit().getNumber(), new Long(865930402721L));
    }

    @Test
    public void invalidGetByAccountId() {
        Response response = handler.getByAccountId(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 400);
        String e = (String) response.getEntity();
        boolean contains = e.contains("Invalid account number provided");
        Assert.assertEquals(contains, Boolean.TRUE);
    }
}
