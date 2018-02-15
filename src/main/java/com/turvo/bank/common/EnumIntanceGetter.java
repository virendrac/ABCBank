package com.turvo.bank.common;

/*
@author Virendra
This class will provide the instance of the proper enum with given value and class type.
*/
public class EnumIntanceGetter {

    public static <T, R extends SuperEnum> R getEnumInstance(Class<R> r, T t) {

        SuperEnum[] obj = r.getEnumConstants();
        for (SuperEnum e : obj){
            if (e.getValue().equals(t)) {
                return (R) e;
            }
      }

        return null;
    }
}