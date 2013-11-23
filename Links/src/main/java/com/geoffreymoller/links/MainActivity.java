package com.geoffreymoller.links;


import android.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LinkListFragment();
    }


}
