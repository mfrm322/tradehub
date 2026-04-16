package com.example.tradehub.exception;

public class AccountExistException extends BaseException{

    public AccountExistException() {
    }

    public AccountExistException(String msg) {
        super(msg);
    }
}
