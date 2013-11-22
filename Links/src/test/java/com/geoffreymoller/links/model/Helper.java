package com.geoffreymoller.links.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by gmoller on 11/22/13.
 */
public class Helper {

    public static JSONObject getLink(String id) throws JSONException {
        Date time = new Date();
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        JSONObject value = new JSONObject();
        value.put("title", "new link");
        value.put("URI", "http://www.foo.com");
        value.put("date", time.getTime());
        JSONArray tags = new JSONArray();
        tags.put("foo");
        value.put("tags", tags);
        obj.put("value", value);
        return obj;
    }

}
