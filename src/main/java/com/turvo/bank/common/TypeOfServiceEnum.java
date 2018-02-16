package com.turvo.bank.common;

public enum TypeOfServiceEnum  implements SuperEnum{
    PREMIUM(1),REGULAR(2);

    private int value;
    TypeOfServiceEnum(int i) {
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
