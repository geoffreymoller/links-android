package com.geoffreymoller.links;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.geoffreymoller.links.model.Link;
import com.geoffreymoller.links.model.LinkCollection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                    LinkCollection.get(getActivity()).refresh(response);
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

        LinkCollection.get(getActivity()).fetch(listener, errorListener);

    }

    private class LinkAdapter extends ArrayAdapter<Link> {

        //@InjectView(R.id.title) TextView title;

        public LinkAdapter(ArrayList<Link> crimes) {
            super(getActivity(), android.R.layout.simple_list_item_1, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_link, null);
            }

            Link l = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.link_list_item_titleTextView);
            titleTextView.setText(l.getTitle());
            TextView URITextView =
                    (TextView)convertView.findViewById(R.id.link_list_item_URITextView);
            URITextView.setText(l.getDate().toString());

            return convertView;
        }
    }

}
