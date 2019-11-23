package com.faizan.revolut.server.logic;

import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.interfaces.Transaction;
import com.faizan.revolut.models.AccountDetails;
import com.faizan.revolut.models.MoneyTransaction;
import com.faizan.revolut.models.incoming.TransferDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionLogicHelper {

    private static final AtomicLong counter = new AtomicLong(0L);
    private static final ConcurrentMap<Long, Transaction> transactions = new ConcurrentHashMap<>();

    public static Transaction getById(Long id) throws InvalidTransactionException {
        if (!transactions.containsKey(id)) {
            throw new InvalidTransactionException();
        }

        return transactions.get(id);
    }

    public static Transaction transfer(TransferDetails details)
            throws InvalidTransactionException, InvalidAccountException {
        AccountDetails debit = AccountsLogicHelper.getById(details.getDebitAccountId());
        AccountDetails credit = AccountsLogicHelper.getById(details.getCreditAccountId());
        Transaction transaction = MoneyTransaction
                .create(counter.incrementAndGet(), debit, credit, details.getAmount());
        transactions.putIfAbsent(transaction.getId(), transaction);
        transaction.run();
        return transaction;
    }

    public static List<Transaction> getByAccountId(Long accountId) throws InvalidAccountException {
        AccountDetails account = AccountsLogicHelper.getById(accountId);
        Predicate<Transaction> predicate = t -> t.getDebit().equals(account) || t.getCredit().equals(account);
        return transactions.values().stream().filter(predicate).collect(Collectors.toList());
    }

    public static List<Transaction> getAll() {
        return new ArrayList<>(transactions.values());
    }
}
