package com.faizan.revolut.models.incoming;

import com.faizan.revolut.fixtures.AccountDetailsFixture;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferDetailsTest {

    private final TransferDetails transferDetails = new TransferDetails();

    @Test
    public void setAmount() {
        transferDetails.setAmount(BigDecimal.TEN);
        Assert.assertEquals(transferDetails.getAmount(), BigDecimal.TEN);
    }

    @Test
    public void setDebitAccountId() {
        transferDetails.setDebitAccountId(AccountDetailsFixture.getDebit().getNumber());
        Assert.assertEquals(transferDetails.getDebitAccountId(), AccountDetailsFixture.getDebit().getNumber());
    }

    @Test
    public void setCreditAccountId() {
        transferDetails.setCreditAccountId(AccountDetailsFixture.getCredit().getNumber());
        Assert.assertEquals(transferDetails.getCreditAccountId(), AccountDetailsFixture.getCredit().getNumber());
    }
}
