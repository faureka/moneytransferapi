package com.faizan.revolut.exceptions;

public class InvalidTransactionException extends ErrorException {

    private final static String json = "{ \"status\" : \"Invalid transaction id provided\" }";

    public InvalidTransactionException() {
        super(json);
    }

    @Override
    public String json() {
        return json;
    }

    @Override
    public int status() {
        return 400;
    }
}
