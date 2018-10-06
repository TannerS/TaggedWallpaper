package io.tanners.taggedwallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import java.util.ArrayList;

import io.tanners.taggedwallpaper.adapters.fragment.FragmentAdapter;
import io.tanners.taggedwallpaper.fragments.image.category.ImagesCategoryFragment;
import io.tanners.taggedwallpaper.fragments.image.order.latest.ImagesLatestFragment;
import io.tanners.taggedwallpaper.fragments.image.order.popular.ImagesPopularFragment;
import io.tanners.taggedwallpaper.interfaces. IGetTag;
import io.tanners.taggedwallpaper.support.builder.snackbar.SimpleSnackBarBuilder;
import io.tanners.taggedwallpaper.support.network.NetworkUtil;

public class CategoryActivity extends TabbedActivity implements IGetTag {
    private final int MAXNUMOFFRAGS = 2;
    public final static String TAG = "SEARCH_QUERY";
    private String tag;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load ui
        setContentView(R.layout.activity_image);
        // other activities pass tag of category or search query using the intent and passed into class as a tag
        // to be fair it should only load this with a tag
        if(getIntent().hasExtra(TAG)) {
            tag = getIntent().getStringExtra(TAG);
        }
        setUpToolBar(R.id.universal_toolbar);
        // set up fragment tabs
        setUpTabs(R.id.universal_view_pager, R.id.universal_tab_layout, MAXNUMOFFRAGS);
        // check for network and/or load fragments
        onNetworkChange(NetworkUtil.isNetworkAvailable(this));
        // set page to be a child of parent activity, this will show the back arrow to return to back activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change title
        getSupportActionBar().setTitle(tag);
    }

    /**
     * check for network functionality
     * TODO prob remove this in main act and here
     * @param isOn
     */
    @Override
    protected void onNetworkChange(boolean isOn) {
        // network connection, load fragments
        if(isOn) {
            // make sure not to reload the fragments
            // since this is acted upon a broadcast receiver
            if(getSize() > 0) {
                loadFragments();
            }
            // no network connection, warn user
        } else {
            SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.image_main),
                    "No network connection",
                    Snackbar.LENGTH_INDEFINITE,
                    "Close");
        }
    }

    protected void loadFragments() {
        // set up fragments into adapter
        setUpFragmentAdapters(new ArrayList<FragmentAdapter.FragmentWrapper>() {{
            add(new FragmentAdapter.FragmentWrapper(ImagesLatestFragment.newInstance(), ImagesLatestFragment.LATEST));
            add(new FragmentAdapter.FragmentWrapper(ImagesPopularFragment.newInstance(), ImagesPopularFragment.POPULAR));
        }});
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
