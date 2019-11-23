package com.faizan.revolut.models.incoming;

import java.math.BigDecimal;

public class TransferDetails {
    private Long debitAccountId;
    private Long creditAccountId;
    private BigDecimal amount;

    public TransferDetails() {
    }

    public TransferDetails(Long debitAccountId, Long creditAccountId, BigDecimal amount) {
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.amount = amount;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Long debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Long creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
