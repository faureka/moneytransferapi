package com.faizan.revolut.server.logic;

import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.interfaces.Party;
import com.faizan.revolut.models.AccountDetails;
import com.faizan.revolut.models.Balance;
import com.faizan.revolut.models.Person;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AccountsLogicHelper {
    private static final Logger logger = LoggerFactory.getLogger(AccountsLogicHelper.class);
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(100_000_000.00d);
    private static final ConcurrentMap<Long, AccountDetails> accounts = new ConcurrentHashMap<>();

    public static void bootstrapAccounts() {
        Party debitParty = new Person("RevolutDebit");
        Long debitAccountNumber = 865930402721L;
        AccountDetails debit = AccountDetails.makeAccount(debitAccountNumber, debitParty, INITIAL_BALANCE);
        accounts.put(debitAccountNumber, debit);
        Party creditParty = new Person("RevolutCredit");
        Long creditAccountNumber = 993646631090L;
        AccountDetails credit = AccountDetails.makeAccount(creditAccountNumber, creditParty, INITIAL_BALANCE);
        accounts.put(creditAccountNumber, credit);
    }

    public static AccountDetails getById(Long id) throws InvalidAccountException {
        if (!accounts.containsKey(id)) {
            throw new InvalidAccountException();
        }

        return accounts.get(id);
    }

    public static List<AccountDetails> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public static String getBalanceById(Long accountNumber) throws InvalidAccountException {
        AccountDetails account = AccountsLogicHelper.getById(accountNumber);
        return new Gson().toJson(new Balance(account.getBalance()));
    }

    public static int size() {
        return accounts.size();
    }
}
