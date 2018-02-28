package com.turvo.bank.common;

public enum ServicesEnum implements SuperEnum{
    DEPOSIT("DEPOSIT") ,WITHDRAWL("WITHDRAWL"),ACCOUNTOPENING("ACCOUNTOPENING"),LOAN("LOAN"),OTHER("OTHER");
    private String value;

    ServicesEnum(String service) {
        value=service;
    }


    @Override
    public String getValue() {
        return value;
    }
}
