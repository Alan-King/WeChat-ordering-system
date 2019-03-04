package com.alan.sell.utils;

import java.util.Random;

public class KeyUtils {
    /**
     * 生成唯一的key
     * 格式：时间+随机数
     */
    public static synchronized String genUniqueKey(){
        long currentTimeMillis = System.currentTimeMillis();
        Random random = new Random();
        int number = random.nextInt(900000)+100000;
        return String.valueOf(currentTimeMillis)+String.valueOf(number);
    }
}
