package com.alan.sell.utils;

import com.alan.sell.enums.EnumCode;

public class EnumUtils {

    public static <T extends EnumCode> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
