package com.geoffreymoller.links.model;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by gmoller on 11/15/13.
 */
@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class LinkTest {

    Link link;
    JSONObject obj;

    @Before
    public void setUp() throws Exception { }

    @Test
    public void shouldBuildLinkObject() throws Exception {
        link = new Link(Helper.getLink("1234"));
        assertEquals(link.getId(), "1234");
        assertEquals(link.getTitle(), "new link");
        assertEquals(link.getURI(), "http://www.foo.com");
        assertEquals(link.getTags(), null);
    }

}
