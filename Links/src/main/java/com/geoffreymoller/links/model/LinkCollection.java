package com.geoffreymoller.links.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.ImmutableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by gmoller on 11/20/13.
 */
public class LinkCollection {

    private static final String TAG = "LinkCollection";
    private String url;

    private static LinkCollection LinkCollection;
    private ArrayList<Link> mLinkCollection;

    private Context mAppContext;

    private LinkCollection(Context appContext) {
        mLinkCollection = new ArrayList<Link>();
        mAppContext = appContext;
    }

    public static LinkCollection get(Context c) {
        if (LinkCollection == null) {
            LinkCollection = new LinkCollection(c.getApplicationContext());
        }
        return LinkCollection;
    }

    public void fetch(String query, Response.Listener listener, Response.ErrorListener errorListener){
        try {
            url = getURL(query);
            RequestQueue queue = Volley.newRequestQueue(mAppContext);
            JsonObjectRequest req = new JsonObjectRequest(url, null, listener, errorListener);
            queue.add(req);
        } catch (Exception e) {
            VolleyLog.e(TAG, "Error loading LinkCollection: ", e);
        }
    }

    private String getURL(String query){
        String uri = "https://geoffreymoller.cloudant.com/collect/_design/";
        Map<String,String> map = ImmutableMap.of("main", "uri/_view/uri?descending=true&limit=200",
                "tag", "tags/_view/tags?descending=true&keys=");
        if(query.length() > 0){
            uri += map.get("tag");
            uri += "[\"" + query + "\"]";
        }
        else {
            uri += map.get("main");
        }
        return uri;
    }

    public ArrayList<Link> refresh(JSONObject response) throws JSONException {
        return LinkCollectionLogic.refresh(response, mLinkCollection);
    }

    public Link getLink(UUID id) {
        return LinkCollectionLogic.getLink(id, mLinkCollection);
    }

    public ArrayList<Link> getLinks() {
        return mLinkCollection;
    }


}
