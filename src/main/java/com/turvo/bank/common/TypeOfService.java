package com.turvo.bank.common;

public enum TypeOfService  implements SuperEnum{
    PREMIUM(1),REGULAR(2);

    private int value;
    TypeOfService(int i) {
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
