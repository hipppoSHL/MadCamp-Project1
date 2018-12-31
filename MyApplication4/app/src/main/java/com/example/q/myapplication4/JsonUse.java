package com.example.q.myapplication4;

import android.support.annotation.Nullable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonUse {
    public static JSONArray dataSet = new JSONArray();

    @Nullable
    public static JSONArray jsonAdd(JSONArray dataSet, int month, int day, @Nullable String note, boolean finish){
        JSONObject data = new JSONObject();
        data.put("month", month);
        data.put("day", day);
        data.put("note", note);
        data.put("finish", false);

        dataSet.add(data);

        return dataSet;
    }



}
