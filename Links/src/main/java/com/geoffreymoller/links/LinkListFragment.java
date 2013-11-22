package com.geoffreymoller.links;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.geoffreymoller.links.model.Links;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gmoller on 11/20/13.
 */
public class LinkListFragment extends ListFragment {

    private static final String TAG = "LinkListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Response.Listener listener = new Response.Listener<JSONObject> () {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    Log.i(TAG, response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        };

        Links.get(getActivity()).fetch(listener, errorListener);

    }

}
