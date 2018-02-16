package com.turvo.bank.common;

public enum  PriorityEnum implements SuperEnum{
    LOW(5),MEDIUM(3),HIGH(1),HIGHVALUE(0);

    private int value;

    PriorityEnum(int i) {
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
