package com.faizan.revolut.server.logic;

import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.fixtures.AccountDetailsFixture;
import com.faizan.revolut.models.AccountDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AccountDetails.class)
public class AccountsLogicHelperTest {
    private static final Logger logger = LoggerFactory.getLogger(AccountsLogicHelperTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        AccountsLogicHelper.bootstrapAccounts();
    }

    @Test
    public void bootstrapAccounts() {
        Assert.assertEquals(AccountsLogicHelper.size(), 2);
    }

    @Test
    public void getAllAccounts() {
        List<AccountDetails> accounts = AccountsLogicHelper.getAll();
        Assert.assertNotNull(accounts);
        Assert.assertEquals(accounts.size(), AccountsLogicHelper.size());
        logger.info(accounts.get(0).getNumber().toString());
        logger.info(accounts.get(1).getNumber().toString());
    }

    @Test
    public void getById() throws InvalidAccountException {
        AccountDetails account = AccountsLogicHelper.getById(865930402721L);
        Assert.assertNotNull(account);
        Assert.assertEquals(account.getNumber(), AccountDetailsFixture.getDebit().getNumber());
        Assert.assertEquals(account.getHolder().getName(), AccountDetailsFixture.getDebit().getHolder().getName());
    }
}
