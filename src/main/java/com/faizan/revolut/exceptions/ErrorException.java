package com.faizan.revolut.exceptions;

public abstract class ErrorException extends Exception {

    protected ErrorException(String message) {
        super(message);
    }

    public abstract String json();

    public abstract int status();
}
