package com.turvo.bank.common;

public enum ServicesEnum implements SuperEnum{
    DEPOSIT("DEPOSIT") ,WITHDRAWL("DEPOSIT"),ACCOUNTOPENING("DEPOSIT"),LOAN("DEPOSIT"),OTHER("DEPOSIT");
    private String value;

    ServicesEnum(String service) {
        value=service;
    }


    @Override
    public String getValue() {
        return value;
    }
}
