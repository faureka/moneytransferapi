package com.faizan.revolut.webservices.rest;

import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.fixtures.AccountDetailsFixture;
import com.faizan.revolut.models.AccountDetails;
import com.faizan.revolut.server.logic.AccountsLogicHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AccountsLogicHelper.class)
public class AccountHandlerTest {
    private static final Logger logger = LoggerFactory.getLogger(AccountHandlerTest.class);
    private AccountHandler handler;

    @Before
    public void setup() throws InvalidAccountException {
        handler = new AccountHandler();
        PowerMockito.mockStatic(AccountsLogicHelper.class);
        PowerMockito.when(AccountsLogicHelper.getAll()).thenReturn(AccountDetailsFixture.all);
        PowerMockito.when(AccountsLogicHelper.getById(1L)).thenReturn(AccountDetailsFixture.debit);
        PowerMockito.when(AccountsLogicHelper.getById(0L)).thenThrow(InvalidAccountException.class);
        PowerMockito.when(AccountsLogicHelper.size()).thenReturn(2);
        PowerMockito.when(AccountsLogicHelper.getBalanceById(1L)).thenReturn(AccountDetailsFixture.debitBalance);
        PowerMockito.when(AccountsLogicHelper.getBalanceById(0L)).thenThrow(InvalidAccountException.class);
    }

    @Test
    public void getAll() {
        Response response = handler.getAllAccounts();
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
        List<AccountDetails> e = (List<AccountDetails>) response.getEntity();
        Assert.assertNotNull(e);
        Assert.assertEquals(e.get(0).getNumber(), AccountDetailsFixture.getDebit().getNumber());
        Assert.assertEquals(e.size(), 2);
    }

    @Test
    public void getById() {
        Response response = handler.getAccountById(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
        AccountDetails e = (AccountDetails) response.getEntity();
        Assert.assertNotNull(e);
        Assert.assertEquals(e.getNumber(), AccountDetailsFixture.getDebit().getNumber());
    }

    @Test
    public void getInvalidId() {
        Response response = handler.getAccountById(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 400);
        String e = (String) response.getEntity();
        boolean contains = e.contains("Invalid account number provided");
        Assert.assertNotNull(e);
        Assert.assertEquals(contains, Boolean.TRUE);
    }

    @Test
    public void getAccountBalance() {
        Response response = handler.getAccountBalance(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
        String e = (String) response.getEntity();
        boolean contains = e.contains(AccountDetailsFixture.debitBalance);
        Assert.assertNotNull(e);
        Assert.assertEquals(contains, Boolean.TRUE);
    }

    @Test
    public void getInvalidAccountBalance() {
        Response response = handler.getAccountBalance(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 400);
        String e = (String) response.getEntity();
        boolean contains = e.contains("Invalid account number provided");
        Assert.assertNotNull(e);
        Assert.assertEquals(contains, Boolean.TRUE);
    }
}
