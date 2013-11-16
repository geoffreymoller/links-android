package com.geoffreymoller.links.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by gmoller on 11/15/13.
 */
@RunWith(RobolectricTestRunner.class)
public class LinkTest {

    Link link;
    JSONObject obj;

    @Before
    public void setUp() throws Exception { }

    @Test
    public void shouldBuildLinkObject() throws Exception {

        Date time = new Date();
        obj = new JSONObject();
        obj.put("id", "1234");
        obj.put("title", "new link");
        obj.put("uri", "http://www.foo.com");
        obj.put("date", time.getTime());
        JSONArray tags = new JSONArray();
        tags.put("foo");
        obj.put("tags", tags);
        link = new Link(obj);

        assertEquals(link.getId(), "1234");
        assertEquals(link.getTitle(), "new link");
        assertEquals(link.getURI(), "http://www.foo.com");
        assertEquals(link.getDate(), time);
        assertEquals(link.getTags(), null);
    }

}
