package com.faizan.revolut.models;

import com.faizan.revolut.constants.Constants;
import com.faizan.revolut.interfaces.Accounts;
import com.faizan.revolut.interfaces.Party;
import com.faizan.revolut.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractAccountDetails implements Accounts {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAccountDetails.class);
    private Long number;
    private Party holder;
    private BigDecimal balance;
    private transient Lock lock;

    AbstractAccountDetails(Long number, Party holder, BigDecimal balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.lock = new ReentrantLock();
    }

    @Override
    public final Long getNumber() {
        return number;
    }

    @Override
    public final BigDecimal getBalance() {
        try {
            lock.lock();
            return balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean debit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        Validator.validateAmountNotNegative(amount);

        try {
            if (lock.tryLock(Constants.WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                try {
                    if (balance.compareTo(amount) > 0) {
                        balance = balance.subtract(amount);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error("Error ::", e);
        }
        return false;
    }

    @Override
    public boolean credit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        Validator.validateAmountNotNegative(amount);

        try {
            if (lock.tryLock(Constants.WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                try {
                    balance = balance.add(amount);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error("Error ::", e);
        }
        return true;
    }

    @Override
    public final Party getHolder() {
        return holder;
    }

    @Override
    public Lock writeLock() {
        return lock;
    }
}
