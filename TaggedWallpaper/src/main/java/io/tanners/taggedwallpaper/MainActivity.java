
package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import io.tanners.taggedwallpaper.support.providers.SearchSuggestionProvider;
import io.tanners.taggedwallpaper.fragments.image.category.ImagesCategoryFragment;
import io.tanners.taggedwallpaper.fragments.image.order.latest.ImagesLatestFragment;
import io.tanners.taggedwallpaper.fragments.image.order.popular.ImagesPopularFragment;
import io.tanners.taggedwallpaper.adapters.fragment.FragmentAdapter;
/*
TODO
        1) viewmodel for all fragments
        2) gridview col num base on screen size
        3) mateiral design for all screens
        4) add favroties fragment
        5) add favorites selection on details activity
        6) add room for favorites, executor, and etc
        7) api guidelines for hot linking
        8) save/restore scroll position (google for guide)
        9)
 */
public class MainActivity extends TabbedActivity {
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
        setUpFragmentAdapters(new ArrayList<FragmentAdapter.FragmentWrapper>() {{
            add(new FragmentAdapter.FragmentWrapper(ImagesCategoryFragment.newInstance(), ImagesCategoryFragment.CATEGORY));
            add(new FragmentAdapter.FragmentWrapper(ImagesPopularFragment.newInstance(), ImagesPopularFragment.POPULAR));
            add(new FragmentAdapter.FragmentWrapper(ImagesLatestFragment.newInstance(), ImagesLatestFragment.LATEST));

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
        // set up search bar for search functionality
        setUpSearchBar(menu);
        // return
        return true;
    }

    /**
     * clear search history
     */
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
                /*
                    When menu item (search bar) is hidden due to screen size
                    must use this here to handle it

                    as said on docs

                    TODO:

                    To handle this situation, the menu item to which you've attached
                    the search widget should activate the search dialog when the user selects
                    it from the overflow menu. In order for it to do so, you must implement
                    onOptionsItemSelected() to handle the "Search" menu item and open the search
                    dialog by calling onSearchRequested().
                 */
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
        // get search menu item
        final MenuItem mSearchBarMenuItem = menu.findItem(R.id.photo_search);
        // create search manager
        SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) mSearchBarMenuItem.getActionView();
        // sets main activity as the searchable activity, check manifest for android:name="android.app.default_searchable"
        ComponentName mComponentName = new ComponentName(this, MainActivity.class);
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(mComponentName));
        // https://stackoverflow.com/questions/18737464/how-to-make-the-action-bar-searchview-fill-entire-action-bar
        mSearchView.setIconifiedByDefault(false);
        // set laytout stuff
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        // load layout stuff
        mSearchView.setLayoutParams(params);
    }

    /**
     * https://stackoverflow.com/a/8896750
     *
     * Loads new intent created by search in same activity
     *
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
     * When the user press the search button / clicks to search
     *
     * @return
     */
    @Override
    public boolean onSearchRequested() {
        startSearch(null, false, new Bundle(), false);
        // return true
        return true;
    }

    /**
     * Used to handle search intents
     *
     * @param intent
     */
    private void handleSearch(Intent intent) {
        // intent is a search intent, to search for images
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // get search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            query = query.trim().toLowerCase();
            // save query into history
            getSearchProvider().saveRecentQuery(query, null);
            // make sure its safe to be put into url for any reason
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    query = URLEncoder.encode(query, StandardCharsets.UTF_8.name());
                else
                    query = URLEncoder.encode(query, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            openIntentForQuery(this, query);
        }
    }

    /**
     * Get search provider
     *
     * @return
     */
    private SearchRecentSuggestions getSearchProvider()
    {
       return new SearchRecentSuggestions(
               this,
            SearchSuggestionProvider.AUTHORITY,
               SearchSuggestionProvider.MODE
       );
    }

    /**
     * Provides a way to provide the same functionality to pass and connect to this intent and pass the tag
     *
     * @param context
     * @param query
     */
    public static void openIntentForQuery(Context context, String query)
    {
        Intent intent = new Intent(context, CategoryActivity.class);
        // pass tag (search query) into activity to load results
        intent.putExtra(CategoryActivity.TAG, query);
        /*
            If set in an Intent passed to Context.startActivity(),
            this flag will cause the launched activity to be brought
            to the front of its task's history stack if it is already running.
         */
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        // start intent
        context.startActivity(intent);
    }

    /**
     * when use clicks back, back on tabs first before activity then closing app (at end)
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