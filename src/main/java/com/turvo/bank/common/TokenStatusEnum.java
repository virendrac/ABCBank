package com.turvo.bank.common;

public enum TokenStatusEnum implements SuperEnum{
    CREATED(10) ,ASSIGNED(20), NEXTCNTR(30),PROCESSING(50),COMPLETED(90),CANCELED(99);

    private int value;
    TokenStatusEnum(int i) {
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }

}
