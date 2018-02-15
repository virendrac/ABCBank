package com.turvo.bank.common;

public enum Services implements SuperEnum{
    DEPOSIT("DEPOSIT") ,WITHDRAWL("DEPOSIT"),ACCOUNTOPENING("DEPOSIT"),LOAN("DEPOSIT"),OTHER("DEPOSIT");
    private String value;

    Services(String service) {
        value=service;
    }


    @Override
    public String getValue() {
        return value;
    }
}
