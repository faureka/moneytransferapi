package com.faizan.revolut.utils;

import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.models.AccountDetails;

import java.math.BigDecimal;

public class Validator {

    public static void validateAmountNotNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static void validateAmountPositive(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static void validateAccountIsDifferent(AccountDetails debit, AccountDetails credit) {
        if (debit.equals(credit)) {
            throw new IllegalArgumentException("Accounts must be different");
        }
    }

    public static <T> void validateMoneyTransaction(Long id, AccountDetails credit, AccountDetails debit,
            BigDecimal amount) throws InvalidTransactionException {
        if (id == null || credit == null || debit == null || amount == null) {
            throw new InvalidTransactionException();
        }
    }
}
