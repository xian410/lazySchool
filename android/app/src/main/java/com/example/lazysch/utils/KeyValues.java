package com.example.lazysch.utils;

import java.util.HashMap;
import java.util.Map;

public class KeyValues {
    private Map<String,Integer> genderLimitSelect;
    private Map<String,String> college;

    private Map<String,String> requireType;


    public KeyValues() {
        genderLimitSelect = new HashMap<>();
        genderLimitSelect.put("不限",0);
        genderLimitSelect.put("仅限男生",1);
        genderLimitSelect.put("仅限女生",2);

        college = new HashMap<>();
        college.put("0","请选择");
        college.put("1","人工智能与数据科学学院");
        college.put("2","电气工程学院");
        college.put("3","材料科学与工程学院");
        college.put("4","化工学院");
        college.put("5","机械工程学院");
        college.put("6","经济管理学院");
        college.put("7","土木与交通学院");
        college.put("8","电子工程学院");
        college.put("9","能源与环境工程学院");
        college.put("10","理学院");
        college.put("11","建筑与艺术设计学院");
        college.put("12","马克思主义学院");
        college.put("13","外国语学院");
        college.put("14","人文与法律学院");
        college.put("15","生命科学与健康工程学院");
        college.put("16","国际教育学院");

    }

    public String getCollege(String key) {
        return college.get(key);
    }

    public int genderToCode(String name) {
        return genderLimitSelect.get(name);
    }

}
