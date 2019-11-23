package com.faizan.revolut.fixtures;

import com.faizan.revolut.models.incoming.TransferDetails;

import java.math.BigDecimal;

public class TransferDetailsFixture {
    private static Long debitAccountId = 1L;
    private static Long creditAccountId = 2L;
    private static BigDecimal amount = BigDecimal.TEN;

    public static TransferDetails get() {
        return new TransferDetails(debitAccountId, creditAccountId, amount);
    }

    public static TransferDetails getInvalid() {
        return new TransferDetails(debitAccountId, null, amount);
    }
}
