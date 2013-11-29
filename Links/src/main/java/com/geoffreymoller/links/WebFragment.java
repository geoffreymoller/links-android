package com.geoffreymoller.links;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by gmoller on 11/29/13.
 */
public class WebFragment extends Fragment {

    WebView v;

    @TargetApi(11)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater, parent, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setTitle(R.string.title);
        }
        Intent i = getActivity().getIntent();
        String url = i.getStringExtra("url");
        v = new WebView(getActivity());
        v.loadUrl(url);
        return v;
    }

}
