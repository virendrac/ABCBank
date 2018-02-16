package com.turvo.bank.exception;

public class ABCBankException extends Exception {
    private String message;
    public ABCBankException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
