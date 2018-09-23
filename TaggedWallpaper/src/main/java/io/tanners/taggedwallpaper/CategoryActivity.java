package io.tanners.taggedwallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import java.util.ArrayList;

import io.tanners.taggedwallpaper.adapters.fragment.FragmentAdapter;
import io.tanners.taggedwallpaper.fragments.image.order.latest.ImagesLatestFragment;
import io.tanners.taggedwallpaper.fragments.image.order.popular.ImagesPopularFragment;
import io.tanners.taggedwallpaper.interfaces. IGetTag;

public class CategoryActivity extends TabbedActivity implements IGetTag {
    private final int MAXNUMOFFRAGS = 2;
    public final static String TAG = "TAG";
    private String tag;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);
        // other activities pass tag of category or search query using the intent and passed into class as a tag
        tag = getIntent().getStringExtra(TAG);
        setUpToolBar(R.id.universal_toolbar);
        // set up fragment tabs
        setUpTabs(R.id.universal_view_pager, R.id.universal_tab_layout, MAXNUMOFFRAGS);
        // set up fragments
        setUpFragmentAdapters(new ArrayList<FragmentAdapter.FragmentWrapper>() {{
            add(new FragmentAdapter.FragmentWrapper(ImagesLatestFragment.newInstance(), ImagesLatestFragment.LATEST));
            add(new FragmentAdapter.FragmentWrapper(ImagesPopularFragment.newInstance(), ImagesPopularFragment.POPULAR));
        }});
        // set page to be a child of parent activity, this will show the back arrow to return to back activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // change title
        getSupportActionBar().setTitle(tag);
    }

    /**
     * before returning to previous activity, make sure to return to previous fragment
     */
    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // return to prev activity
            super.onBackPressed();
        } else {
            // otherwise, select the previous fragment
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
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
     * get current tag (search result, clicked category, etc)
     * @return
     */
    @Override
    public String getTag() {
        return this.tag;
    }
}
