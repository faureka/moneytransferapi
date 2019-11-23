package com.faizan.revolut.models;

import com.faizan.revolut.constants.Constants;
import com.faizan.revolut.enums.TransactionState;
import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.interfaces.Transaction;
import com.faizan.revolut.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class MoneyTransaction implements Transaction {
    private static final Logger logger = LoggerFactory.getLogger(MoneyTransaction.class);

    private Long id;
    private AccountDetails debit;
    private AccountDetails credit;
    private BigDecimal amount;
    private TransactionState state;

    public MoneyTransaction() {
    }

    private MoneyTransaction(Long id, AccountDetails debit, AccountDetails credit, BigDecimal amount)
            throws InvalidTransactionException {
        Validator.validateMoneyTransaction(id, credit, debit, amount);
        Validator.validateAmountPositive(amount);
        Validator.validateAccountIsDifferent(debit, credit);

        this.id = id;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.state = TransactionState.NEW;
    }

    public static Transaction create(Long id, AccountDetails debit, AccountDetails credit, BigDecimal amount)
            throws InvalidTransactionException {
        return new MoneyTransaction(id, debit, credit, amount);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public AccountDetails getDebit() {
        return debit;
    }

    @Override
    public AccountDetails getCredit() {
        return credit;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public TransactionState getState() {
        return state;
    }

    @Override
    public synchronized boolean run() {
        if (state != TransactionState.COMPLETED) {
            changeState();
            return doRun();
        }
        return false;
    }

    private boolean doRun() {
        final Lock debitLock = debit.writeLock();
        try {
            if (debitLock.tryLock(Constants.WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                try {
                    final Lock creditLock = credit.writeLock();
                    if (creditLock.tryLock(Constants.WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                        try {
                            if (debit.debit(amount)) {
                                if (credit.credit(amount)) {
                                    state = TransactionState.COMPLETED;
                                    logger.trace("Transaction {} completed", id);
                                    return true;
                                }
                            }
                            state = TransactionState.INSUFFICIENT_FUNDS;
                        } finally {
                            creditLock.unlock();
                        }
                    } else {
                        state = TransactionState.CONCURRENCY_ERROR;
                    }
                } finally {
                    debitLock.unlock();
                }
            } else {
                state = TransactionState.CONCURRENCY_ERROR;
            }
        } catch (InterruptedException e) {
            state = TransactionState.CONCURRENCY_ERROR;
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    private void changeState() {
        switch (state) {
        case INSUFFICIENT_FUNDS:
        case CONCURRENCY_ERROR:
            state = TransactionState.RESTARTED;
            break;
        }
    }
}
