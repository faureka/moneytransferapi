package com.faizan.revolut.interfaces;

import com.faizan.revolut.enums.TransactionState;

import java.math.BigDecimal;

public interface Transaction {

    Long getId();

    Accounts getDebit();

    Accounts getCredit();

    BigDecimal getAmount();

    TransactionState getState();

    boolean run();
}
