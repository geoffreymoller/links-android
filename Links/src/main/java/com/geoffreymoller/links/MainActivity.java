package com.geoffreymoller.links;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;

public class MainActivity extends SingleFragmentActivity {

    //TODO - webview
    //TODO - fix onResume Activity crash

    private static final String TAG = "MainActivity";

    @Override
    protected Fragment createFragment() {
        return new LinkListFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        String query = intent.getStringExtra(SearchManager.QUERY);
        //TODO - set non-generic fragment id
        LinkListFragment f = (LinkListFragment) getFragmentManager().findFragmentById(R.id.fragmentContainer);
        f.search(query);
    }


}
