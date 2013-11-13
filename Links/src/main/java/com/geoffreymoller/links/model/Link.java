package com.geoffreymoller.links.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gmoller on 11/12/13.
 */
public class Link {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_URI = "uri";
    private static final String JSON_DATE = "date";
    private static final String JSON_TAGS = "tags";

    private String mId;
    private String mTitle;
    private String mURI;
    private Date mDate;
    private String[] mTags;

    public Link(JSONObject json) throws JSONException {
        mId = json.getString(JSON_ID);
        mTitle = json.getString(JSON_TITLE);
        mURI = json.getString(JSON_URI);
        mDate = new Date(json.getLong(JSON_DATE));
        JSONArray ary = json.getJSONArray(JSON_TAGS);
        List<String> list = new ArrayList<String>();
        for(byte i = 0; i < ary.length(); i++){
            list.add(ary.getString(i));
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getURI() {
        return mURI;
    }

    public void setURI(String URI) {
        mURI = URI;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String[] getTags() {
        return mTags;
    }

    public void setTags(String[] tags) {
        mTags = tags;
    }

}
