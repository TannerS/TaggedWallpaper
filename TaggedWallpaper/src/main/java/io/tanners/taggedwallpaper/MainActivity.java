
package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import io.tanners.taggedwallpaper.fragments.CategoryFragment;
import io.tanners.taggedwallpaper.fragments.LatestImagesFragment;
import io.tanners.taggedwallpaper.fragments.PopularImagesFragment;
import io.tanners.taggedwallpaper.adapters.FragmentAdapter;

// TODO message when no search results are found for image

public class  MainActivity extends TabbedActivity {
//    private ActionBarDrawerToggle mToggle;
    private final int MAXNUMOFFRAGS = 3;
    private SearchManager mSearchManager;
    private SearchView mSearchView;

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

    private void clearSearchHistory()
    {
        getSearchProvider().clearHistory();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.clear_search_history:
                clearSearchHistory();
                return true;
            case R.id.photo_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param menu
     */
    private void setUpSearchBar(Menu menu)
    {
        final MenuItem mSearchBarMenuItem = menu.findItem(R.id.photo_search);
        // create search manager
        mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) mSearchBarMenuItem.getActionView();
        // sets main activity as the searchable activity, check manifest for android:name="android.app.default_searchable"
        ComponentName mComponentName = new ComponentName(this, MainActivity.class);
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(mComponentName));
        // https://stackoverflow.com/questions/18737464/how-to-make-the-action-bar-searchview-fill-entire-action-bar
        mSearchView.setIconifiedByDefault(false);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mSearchView.setLayoutParams(params);
    }

    /**
     * @param newConfig
     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggles
//        mToggle.onConfigurationChanged(newConfig);
//        mToggle.syncState();
//    }

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
     * https://developer.android.com/guide/topics/search/search-dialog
     *
     * @return
     */
    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        startSearch(null, false, appData, false);
        return true;
    }






    /**
     * Used to handle search intents
     * @param intent
     */
    private void handleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // get search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            query = query.trim().toLowerCase();

            getSearchProvider().saveRecentQuery(query, null);

            // make sure its safe to be put into url for any reason, including security
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    query = URLEncoder.encode(query, StandardCharsets.UTF_8.name());
                else {
                        query = URLEncoder.encode(query, "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            openIntentForQuery(this, query);
        }
    }


    private SearchRecentSuggestions getSearchProvider()
    {
       return new SearchRecentSuggestions(this,
            SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
    }


    /**
     * Provides a way to provide the same functionality to pass and connect to this intent and pass the tag
     *
     * @param context
     * @param query
     */
    public static void openIntentForQuery(Context context, String query)
    {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(ImageActivity.TAG, query);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
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