package com.faizan.revolut.models;

import com.faizan.revolut.constants.Constants;

import java.math.BigDecimal;

public class AccountDetails extends AbstractAccountDetails {

    public AccountDetails() {
    }

    public AccountDetails(Long number, Person holder, BigDecimal balance) {
        super(number, holder, balance);
    }

    public static AccountDetails makeAccount(Long number, Person holder, BigDecimal balance) {
        return new AccountDetails(number, holder, balance);
    }

    public static AccountDetails makeAccountWithDefaultBalance(Long number, Person holder) {
        return AccountDetails.makeAccount(number, holder, Constants.MIN_BALANCE);
    }
}
