package com.faizan.revolut.models;

import com.faizan.revolut.constants.Constants;
import com.faizan.revolut.interfaces.Party;

import java.math.BigDecimal;

public class AccountDetails extends AbstractAccountDetails {

    public AccountDetails(Long number, Party holder, BigDecimal balance) {
        super(number, holder, balance);
    }

    public static AccountDetails makeAccount(Long number, Party holder, BigDecimal balance) {
        return new AccountDetails(number, holder, balance);
    }

    public static AccountDetails makeAccountWithDefaultBalance(Long number, Party holder) {
        return AccountDetails.makeAccount(number, holder, Constants.MIN_BALANCE);
    }
}
