package com.turvo.bank.common;

public enum TokenStatus implements SuperEnum{
    CREATED(10) ,ASSIGNED(20), NEXTCNTR(30),PROCESSING(50),COMPLETED(90),CANCELED(99);

    private int value;
    TokenStatus(int i) {
        value=i;
    }

    @Override
    public Integer getValue() {
        return value;
    }

}
