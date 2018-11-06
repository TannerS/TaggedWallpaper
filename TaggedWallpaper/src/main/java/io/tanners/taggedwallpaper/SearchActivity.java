package io.tanners.taggedwallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import java.io.UnsupportedEncodingException;
import io.tanners.taggedwallpaper.fragments.image.search.ImagesSearchFragment;
import io.tanners.taggedwallpaper.interfaces. IGetTag;
import io.tanners.taggedwallpaper.support.network.encoder.EncoderUtil;

public class SearchActivity extends SupportActivity implements IGetTag {
    public final static String SEARCH_QUERY_INTENT_EXTRA_KEY = "SEARCH_QUERY_INTENT_EXTRA";
    private String mSearchQuery;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load ui
        setContentView(R.layout.activity_search);
        // other activities pass mSearchQuery of category or search query using the intent and passed into class as a mSearchQuery
        // to be fair it should only load this with a mSearchQuery
        if(getIntent().hasExtra(SEARCH_QUERY_INTENT_EXTRA_KEY)) {
            try {
                mSearchQuery = getIntent().getStringExtra(SEARCH_QUERY_INTENT_EXTRA_KEY);
                mSearchQuery = EncoderUtil.encode(mSearchQuery);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        setUpToolBar(R.id.universal_toolbar);
        loadFragments(savedInstanceState);
        // set page to be a child of parent activity, this will show the back arrow to return to back activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change title
        getSupportActionBar().setTitle(mSearchQuery);
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

    protected void loadFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ImagesSearchFragment mFragment = null;

        if (savedInstanceState == null) {
            mFragment = ImagesSearchFragment.newInstance(mSearchQuery);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.search_fragment_container, mFragment, ImagesSearchFragment.FRAGMENT_TAG);
            fragmentTransaction.commit();
        } else {
            mFragment = (ImagesSearchFragment) getSupportFragmentManager().findFragmentByTag(ImagesSearchFragment.FRAGMENT_TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.search_fragment_container, mFragment);
            fragmentTransaction.commit();
        }
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
     * get current mSearchQuery (search result, clicked category, etc)
     * @return
     */
    @Override
    public String getmSearchQuery() {
        return this.mSearchQuery;
    }
}
