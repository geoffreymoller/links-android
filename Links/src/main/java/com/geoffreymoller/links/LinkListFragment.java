package com.geoffreymoller.links;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.geoffreymoller.links.model.Links;
import com.geoffreymoller.links.model.RequestHandler;

import org.json.JSONObject;

/**
 * Created by gmoller on 11/20/13.
 */
public class LinkListFragment extends ListFragment {

    private static final String TAG = "LinkListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestHandler r = new RequestHandler() {
            @Override
            public void onResponse(JSONObject response) {
               Log.i(TAG, response.toString());
            }
        };

        Links.get(getActivity()).fetch(r);

    }

}
