package io.tanners.taggedwallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import java.io.UnsupportedEncodingException;
import io.tanners.taggedwallpaper.fragments.image.search.ImagesSearchFragment;
import io.tanners.taggedwallpaper.interfaces. IGetTag;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;
import io.tanners.taggedwallpaper.support.network.encoder.EncoderUtil;

public class SearchActivity extends SupportActivity implements IGetTag {
    public final static String TAG = "SEARCH_QUERY";
    private String tag;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load ui
        setContentView(R.layout.activity_search);
        // other activities pass tag of category or search query using the intent and passed into class as a tag
        // to be fair it should only load this with a tag
        if(getIntent().hasExtra(TAG)) {
            try {
                tag = getIntent().getStringExtra(TAG);
                tag = EncoderUtil.encode(tag);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        setUpToolBar(R.id.universal_toolbar);
        // check for network and/or load fragments
        onNetworkChange(NetworkUtil.isNetworkAvailable(this));
        loadFragments();
        // set page to be a child of parent activity, this will show the back arrow to return to back activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change title
        getSupportActionBar().setTitle(tag);
    }

    /**
     * check for network functionality
     *
     * @param isOn
     */
    @Override
    protected void onNetworkChange(boolean isOn) {
        // not used right now
    }

    protected void loadFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImagesSearchFragment mFragment = ImagesSearchFragment.newInstance(tag);
        fragmentTransaction.add(R.id.search_fragment_container, mFragment);
        fragmentTransaction.commit();
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // https://developer.android.com/training/implementing-navigation/ancestral.html
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * get current tag (search result, clicked category, etc)
     * @return
     */
    @Override
    public String getTag() {
        return this.tag;
    }
}
