package com.geoffreymoller.links.model;

import org.json.JSONObject;

/**
 * Created by gmoller on 11/20/13.
 */
public interface IRequestHandler {
    public void onResponse(JSONObject object);
}
