package com.udacity.sandwichclub.utils;

import android.provider.ContactsContract;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonUtils {

    final static String NAME = "name";
    final static String MAIN_NAME = "mainName";
    final static String ORIGIN = "placeOfOrigin";
    final static String DESCRIPTION = "description";
    final static String IMAGE = "image";
    final static String AKA = "alsoKnownAs";
    final static String INGREDIENTS = "ingredients";

    private static List<String> ParseList(JSONObject obj, String key) {
        JSONArray data = null;
        List<String> list = new ArrayList<>();
        try {
            data = obj.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data != null && data.length() > 0) {
            for (int i = 0; i < data.length(); i++) {
                try {
                    list.add(data.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            list.add("-");
        }
        return list;
    }

    private static String getValue(JSONObject obj, String key) {
        try {
            String _value = obj.getString(key);
            if (_value == null || _value.length() == 0)
                return "-";
            return _value;
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Sandwich parseSandwichJson(String json) {

        JSONObject tmp = null, name = null;

        try {
            tmp = new JSONObject(json);
            name = tmp.getJSONObject(NAME);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        String mainName = getValue(name, MAIN_NAME);
        String placeOfOrigin = getValue(tmp, ORIGIN);
        String description = getValue(tmp, DESCRIPTION);
        String image = getValue(tmp, IMAGE);
        List<String> alsoKnownAs = JsonUtils.ParseList(name, AKA);
        List<String> ingredients = JsonUtils.ParseList(tmp, INGREDIENTS);

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
