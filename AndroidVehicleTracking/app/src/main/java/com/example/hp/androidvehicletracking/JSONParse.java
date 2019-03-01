package com.example.hp.androidvehicletracking;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 8/30/2016.
 */
public class JSONParse {
    public String mainparse(JSONObject json) {
        String p = "parse";
        try {
            p = json.getString("Value");
        } catch (JSONException e) {
            p = e.getMessage();
        }
        return p;
    }
}
