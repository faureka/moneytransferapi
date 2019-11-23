package com.faizan.revolut.fixtures;

import com.faizan.revolut.constants.Constants;
import com.faizan.revolut.models.AccountDetails;
import com.faizan.revolut.models.Balance;
import com.faizan.revolut.models.Person;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AccountDetailsFixture {

    public static AccountDetails debit = getDebit();
    public static AccountDetails credit = getCredit();
    public static List<AccountDetails> all = getAll();
    public static String debitBalance = new Gson().toJson(new Balance(debit.getBalance()));

    private Person holder = new Person("Revolut");
    private Long accountNumber = 315457352686L;
    private AccountDetails accountDetails = AccountDetails.makeAccountWithDefaultBalance(accountNumber, holder);

    public static AccountDetails getDebit() {
        Person debitParty = new Person("RevolutDebit");
        Long debitAccountNumber = 865930402721L;
        return new AccountDetails(debitAccountNumber, debitParty, Constants.MIN_BALANCE);
    }

    public static AccountDetails getCredit() {
        Person creditParty = new Person("RevolutCredit");
        Long creditAccountNumber = 993646631090L;
        return new AccountDetails(creditAccountNumber, creditParty, Constants.MIN_BALANCE);
    }

    private static List<AccountDetails> getAll() {
        List<AccountDetails> a = new ArrayList<>();
        a.add(AccountDetailsFixture.getDebit());
        a.add(AccountDetailsFixture.getCredit());
        return a;
    }

    public String get() {
        return new Gson().toJson(accountDetails);
    }
}
