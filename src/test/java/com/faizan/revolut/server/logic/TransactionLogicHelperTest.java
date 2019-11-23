package com.faizan.revolut.server.logic;

import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.fixtures.AccountDetailsFixture;
import com.faizan.revolut.fixtures.TransferDetailsFixture;
import com.faizan.revolut.interfaces.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AccountsLogicHelper.class })
public class TransactionLogicHelperTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(AccountsLogicHelper.class);
        PowerMockito.when(AccountsLogicHelper.class, "getById", 1L).thenReturn(AccountDetailsFixture.debit);
        PowerMockito.when(AccountsLogicHelper.class, "getById", 2L).thenReturn(AccountDetailsFixture.credit);
    }

    @Test
    public void validTxn() throws Exception {
        Transaction txn = TransactionLogicHelper.transfer(TransferDetailsFixture.get());
        Assert.assertNotNull(txn);
        Assert.assertEquals(txn.getCredit().getNumber(), new Long(993646631090L));
        Assert.assertEquals(txn.getDebit().getNumber(), new Long(865930402721L));
        Assert.assertNotNull(txn.getId());
    }

    @Test
    public void invalidTxn() throws Exception {
        thrown.expect(InvalidTransactionException.class);
        thrown.expectMessage("Invalid transaction id provided");
        TransactionLogicHelper.transfer(TransferDetailsFixture.getInvalid());
    }
}
