package com.faizan.revolut.exceptions;

public class InvalidAccountException extends ErrorException {
    private final static String json = "{ \"status\" : \"Invalid account number provided\" }";

    public InvalidAccountException() {
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
