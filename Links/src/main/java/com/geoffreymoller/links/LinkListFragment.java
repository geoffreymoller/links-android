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
import java.util.Comparator;

/**
 * Created by gmoller on 11/20/13.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LinkListFragment extends ListFragment {

    private static final String TAG = "LinkListFragment";
    private static final int PAINT_MODE_RESPONSE = 1;
    private static final int PAINT_MODE_SEARCH = 2;

    private String query = "";
    private LinkAdapter adapter;
    private ArrayList<Link> links;
    private MenuItem searchMenuItem;
    private Menu optionsMenu;

    public String getQuery() {
        return query != null ? query : "";
    }

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

    @Override
    public void onResume(){
        super.onResume();
        query = getQuery();
    }

    public boolean search(String searchQuery){
        query = searchQuery;
        paintUI(PAINT_MODE_SEARCH);
        LinkCollection.get(getActivity()).fetch(query, listener, errorListener);
        return true;
    }

    public void onResponse(JSONObject response) throws JSONException{

        paintUI(PAINT_MODE_RESPONSE);

        LinkCollection collection = LinkCollection.get(getActivity());
        collection.refresh(response);
        links = collection.getLinks();

        if(adapter == null){
            adapter = new LinkAdapter(links);
            adapter.sort(getComparator());
            setListAdapter(adapter);
        }
        else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.sort(getComparator());
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public Comparator<Link> getComparator(){
        return new Comparator<Link>() {
            @Override
            public int compare(Link lhs, Link rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        };
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
       View v = super.onCreateView(inflater, parent, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setTitle(R.string.title);
        }
       return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        Link link = ((LinkAdapter)getListAdapter()).getItem(position);
        Intent i;
        String URI = link.getURI();
        if(true){ //TODO - build settings activity, add Browse section
            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URI));
        } else {
            //TODO - progressbar
            i = new Intent(getActivity(), WebActivity.class);
            i.putExtra("url", URI);
        }
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.optionsMenu = menu;
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchMenuItem = menu.findItem(R.id.search);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){
       switch (item.getItemId()){
           case android.R.id.home:
               return search("");
           case R.id.refresh:
                updateRefreshButton(true);
                return search(query);
           default:
                return true;
       }
    }

    public void updateRefreshButton(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu.findItem(R.id.refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
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

    private void paintUI(Integer mode){
        switch (mode){
            case PAINT_MODE_RESPONSE:
                if(searchMenuItem != null){
                    searchMenuItem.collapseActionView();
                }
                updateRefreshButton(false);
                break;
            case PAINT_MODE_SEARCH:
                updateRefreshButton(true);
                if (!(query.length() == 0)){
                    getActivity().getActionBar().setSubtitle("Tag: " + query);
                    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
                }
                else{
                    getActivity().getActionBar().setSubtitle("Tag: All");
                    getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                }
                break;
        }
    }

}
