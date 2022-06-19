package com.example.lazysch.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class PageRequest {
    public static JSONObject getPage() throws JSONException {
        JSONObject page = new JSONObject();
        page.put("current",1);
        page.put("size",30);
        return page;
    }
}
