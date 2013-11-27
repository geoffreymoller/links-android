package com.geoffreymoller.links;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LinkListFragment extends ListFragment {

    private static final String TAG = "LinkListFragment";
    private String linkTag = "";
    private LinkAdapter adapter;
    private ArrayList<Link> links;

    @TargetApi(11)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent i = getActivity().getIntent();
        String query = "";
        if (Intent.ACTION_SEARCH.equals(i.getAction())) {
            query = i.getStringExtra(SearchManager.QUERY);
        }
        search(query);
    }

    public void search(String query){
        LinkCollection.get(getActivity()).fetch(query, listener, errorListener);
    }

    public void onResponse(JSONObject response) throws JSONException{
        LinkCollection collection = LinkCollection.get(getActivity());
        collection.refresh(response);
        links = collection.getLinks();
        if(adapter == null){
            adapter = new LinkAdapter(links);
            setListAdapter(adapter);
        }
        else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
       View v = super.onCreateView(inflater, parent, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setTitle(R.string.title);
            if (!(linkTag.length() == 0)){
                getActivity().getActionBar().setSubtitle("Tag: " + linkTag);
            }
        }
       return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        Link link = ((LinkAdapter)getListAdapter()).getItem(position);
        String URI = link.getURI();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(URI));
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){
       switch (item.getItemId()){
           case R.id.refresh:
                return true;
           default:
                return true;
       }
    }

    private class LinkAdapter extends ArrayAdapter<Link> {

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
            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.link_list_item_dateTextView);
            dateTextView.setText(l.getDate().toString());
            TextView URITextView =
                    (TextView)convertView.findViewById(R.id.link_list_item_URITextView);
            URITextView.setText(l.getURI());

            return convertView;
        }
    }

    Response.Listener listener = new Response.Listener<JSONObject> () {
        @Override
        public void onResponse(JSONObject response) {
            try {
                VolleyLog.v("Response:%n %s", response.toString(4));
                LinkListFragment.this.onResponse(response);
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



}
