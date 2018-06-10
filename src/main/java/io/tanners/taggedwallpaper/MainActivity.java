
package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import io.tanners.taggedwallpaper.fragments.CategoryFragment;
import io.tanners.taggedwallpaper.fragments.LatestImagesFragment;
import io.tanners.taggedwallpaper.fragments.PopularImagesFragment;
import io.tanners.taggedwallpaper.adapters.FragmentAdapter;

public class MainActivity extends TabbedActivity {
    private ActionBarDrawerToggle mToggle;
    private final int MAXNUMOFFRAGS = 3;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar(R.id.universal_toolbar);
        // set up fragment tabs
        setUpTabs(R.id.universal_view_pager, R.id.universal_tab_layout, MAXNUMOFFRAGS);
        // set up fragments into adapter
        setUpFragmentAdapters(new ArrayList<FragmentAdapter.FragmentInfo>() {{
            add(new FragmentAdapter.FragmentInfo(CategoryFragment.newInstance(), CategoryFragment.CATEGORY));
            add(new FragmentAdapter.FragmentInfo(PopularImagesFragment.newInstance(), PopularImagesFragment.POPULAR));
            add(new FragmentAdapter.FragmentInfo(LatestImagesFragment.newInstance(), LatestImagesFragment.LATEST));

        }});

        // handle search queries
        // used for start, may not be needed
        handleSearch(getIntent());
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_bar, menu);
        setUpSearchBar(menu);
        return true;
    }

    /**
     * @param menu
     */
    private void setUpSearchBar(Menu menu)
    {
        final MenuItem mSearchBarMenuItem = menu.findItem(R.id.photo_search);
        // create search manager
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView mSearchView = (SearchView) mSearchBarMenuItem.getActionView();
        // sets main activity as the searchable activity, check manifest for android:name="android.app.default_searchable"
        ComponentName mComponentName = new ComponentName(this, MainActivity.class);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(mComponentName));
        // https://stackoverflow.com/questions/18737464/how-to-make-the-action-bar-searchview-fill-entire-action-bar
        mSearchView.setIconifiedByDefault(false);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mSearchView.setLayoutParams(params);
    }

    /**
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mToggle.onConfigurationChanged(newConfig);
        mToggle.syncState();
    }

    /**
     *  https://developer.android.com/training/search/setup.html
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // handle search queries
        handleSearch(intent);
    }

    /**
     * Used to handle search intents
     * @param intent
     */
    private void handleSearch(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // get search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            ImageActivity.openIntentForQuery(this, query);
        }
    }

    /**
     * when use clicks back
     */
    @Override
    public void onBackPressed() {
        // cycle fragments to first one
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }
}