package com.alan.sell.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;

public class JsonUtils {
    private static  GsonBuilder gsonBuilder = new GsonBuilder();

    public static String toJSON(Object obj){
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        return gson.toJson(obj);
    }

}
