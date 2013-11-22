package com.geoffreymoller.links.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by gmoller on 11/22/13.
 */
public class LinkCollectionLogic {

    public static ArrayList<Link> refresh(JSONObject response, ArrayList<Link> mLinkCollection) throws JSONException {
        mLinkCollection.clear();
        JSONArray links = response.getJSONArray("rows");
        for (int i = 0; i < links.length(); i++) {
            JSONObject link = links.getJSONObject(i);
            Link _link = new Link(link);
            mLinkCollection.add(_link);
        }
        return mLinkCollection;
    }

    public static Link getLink(UUID id, ArrayList<Link> mLinkCollection) {
        for (Link l : mLinkCollection) {
            if (l.getId().equals(id))
                return l;
        }
        return null;
    }
}
