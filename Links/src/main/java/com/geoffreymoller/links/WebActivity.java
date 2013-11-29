package com.geoffreymoller.links;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;

public class WebActivity extends SingleFragmentActivity {

    private static final String TAG = "WebActivity";

    @Override
    protected Fragment createFragment() {
        return new WebFragment();
    }

}
