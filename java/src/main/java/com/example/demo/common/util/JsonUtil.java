package com.example.demo.common.util;

import com.google.gson.Gson;

/**
 * Json处理工具
 * 使用Gson
 * @author zuozhiwei
 */
public class JsonUtil {
    /**
     * 单例Gson对象
     */
    private static Gson gson = null;

    /**
     * 获取单例Gson对象
     */
    public static void getGson() {
        if (gson == null) {
            synchronized (JsonUtil.class) {
                gson = new Gson();
            }
        }
    }

    /**
     * 对象在转json
     * @param o 待转换对象
     * @return  json字符串
     */
    public static String toJson(Object o) {
        getGson();
        return gson.toJson(o);
    }
}
