package com.turvo.bank.common;

public enum  Priority implements SuperEnum{
    LOW(5),MEDIUM(3),HIGH(1),HIGHVALUE(0);

    private int value;

    Priority(int i) {
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
