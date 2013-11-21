package com.geoffreymoller.links.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by gmoller on 11/20/13.
 */
public class Links {

    private static final String TAG = "Links";
    private final String url = "https://geoffreymoller.cloudant.com/collect/_design/uri/_view/uri?descending=true&limit=50";

    private static Links Links;
    private ArrayList<Link> mLinks;

    private Context mAppContext;

    private Links(Context appContext) {
        mAppContext = appContext;
    }

    public static Links get(Context c) {
        if (Links == null) {
            Links = new Links(c.getApplicationContext());
        }
        return Links;
    }

    public void fetch(RequestHandler handler){
        final RequestHandler handle = handler;
        try {
            RequestQueue queue = Volley.newRequestQueue(mAppContext);
            JsonObjectRequest req = new JsonObjectRequest(url, null, new Response.Listener<JSONObject> () {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        VolleyLog.v("Response:%n %s", response.toString(4));
                        handle.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });
            queue.add(req);
        } catch (Exception e) {
            VolleyLog.e(TAG, "Error loading links: ", e);
        }
    }

    public Link getLink(UUID id) {
        for (Link l : mLinks) {
            if (l.getId().equals(id))
                return l;
        }
        return null;
    }

    public ArrayList<Link> getLinks() {
        return mLinks;
    }


}
