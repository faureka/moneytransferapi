package com.faizan.revolut.interfaces;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

public interface Accounts {

    Long getNumber();

    BigDecimal getBalance();

    boolean debit(BigDecimal amount);

    boolean credit(BigDecimal amount);

    Party getHolder();

    Lock writeLock();
}
