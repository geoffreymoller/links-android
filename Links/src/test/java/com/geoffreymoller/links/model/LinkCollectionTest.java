package com.geoffreymoller.links.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by gmoller on 11/15/13.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class LinkCollectionTest {

    ArrayList<Link> links;
    JSONObject obj;

    @Before
    public void setUp() throws Exception { }

    @Test
    public void shouldRefreshToZeroWithEmptyInput() throws Exception {
        ArrayList<Link> newLinks = new ArrayList<Link>();
        JSONArray ary = new JSONArray(newLinks);
        JSONObject obj = new JSONObject().put("rows", ary);
        ArrayList<Link> previousLinks = new ArrayList<Link>();
        previousLinks.add(new Link(Helper.getLink("1")));
        ArrayList<Link> _links = LinkCollectionLogic.refresh(obj, previousLinks);
        assertEquals(_links.size(), 0);
    }

    @Test
    public void shouldReplaceOldLinksWithNew() throws Exception {
        ArrayList<JSONObject> newLinks = new ArrayList<JSONObject>();
        newLinks.add(Helper.getLink("1"));
        newLinks.add(Helper.getLink("2"));
        JSONArray ary = new JSONArray(newLinks);
        JSONObject obj = new JSONObject();
        obj.put("rows", ary);
        ArrayList<Link> previousLinks = new ArrayList<Link>();
        previousLinks.add(new Link(Helper.getLink("4")));
        ArrayList<Link> _links = LinkCollectionLogic.refresh(obj, previousLinks);
        assertEquals(_links.size(), 2);
    }

}
