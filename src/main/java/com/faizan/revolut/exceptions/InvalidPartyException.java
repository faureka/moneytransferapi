package com.faizan.revolut.exceptions;

public class InvalidPartyException extends ErrorException {
    private final static String json = "{ \"status\" : \"Invalid party id provided\" }";

    public InvalidPartyException() {
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
