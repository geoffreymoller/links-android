package com.geoffreymoller.links;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;

public class MainActivity extends SingleFragmentActivity {

    //TODO - webview
    //TODO - stash and retrieve search term on suspend

    private static final String TAG = "MainActivity";

    @Override
    protected Fragment createFragment() {
        return new LinkListFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        String query = intent.getStringExtra(SearchManager.QUERY);
        query = query != null ? query : "";
        //TODO - set non-generic fragment id
        LinkListFragment f = (LinkListFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        f.search(query);
    }


}
