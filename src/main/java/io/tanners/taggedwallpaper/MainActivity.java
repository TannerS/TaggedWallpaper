
package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.FragmentAdapter;
import io.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;

public class MainActivity extends AppCompatActivity implements IFindFragment{
    private List<FragmentAdapter.FragmentInfo> frags;
    private ViewPager mViewPager;
    private final int FRAG_AMOUNT =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setHasOptionsMenu(true);

        setUpToolBar();
        loadResources();
        // set up fragment tabs
        setUpTabs();
        // set up fragments into adapter
        setUpFragmentAdapters();
        // handle search queries
        handleSearch(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_bar, menu);
        setUpSearchBar(menu);
        return true;
    }

    private void setUpSearchBar(Menu menu)
    {
        final MenuItem mSearchBarMenuItem = menu.findItem(R.id.photo_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search_view = (SearchView) mSearchBarMenuItem.getActionView();

        mSearchBarMenuItem.setIcon(R.drawable.ic_action_search_white);
        mSearchBarMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
        //mSearchBarMenuItem.setTitle("TITLE");

        ComponentName mComponentName = new ComponentName(getApplicationContext(), MainActivity.class);
        search_view.setSearchableInfo(searchManager.getSearchableInfo(mComponentName));

//        search_view.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        search_view.setQueryHint("HINT HERE");
        search_view.setIconifiedByDefault(true);
        search_view.setQueryHint("Search Images");
//        search_view.setQuery("", false);
//        int searchTextId = search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);

//        TextView searchText = (TextView) search_view.findViewById(searchTextId);

//        if (searchText!=null) {
//            searchText.setTextColor(Color.WHITE);
//            searchText.setHintTextColor(Color.WHITE);
//        }

//        my_search_view.setIconified(false);
//        my_search_view.setIconifiedByDefault(false);



//        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

//        int id = search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) search_view.findViewById(id);
//        textView.setHint("Search location...");
//        textView.setHintTextColor(getResources().getColor(R.color.cardview_dark_background));
//        textView.setTextColor(getResources().getColor(R.color.colorAccent));

        final SearchView finalSearch_view = search_view;
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                final String tag = finalSearch_view.getQuery().toString();
                new Runnable() {
                    @Override
                    public void run() {
//                        MenuItemCompat.collapseActionView(search_bar);
//                        search_bar.collapseActionView();
//                        search_view.clearFocus();
//                        search_view.setQuery("", false);
//                        search_view.setFocusable(false);
//                        searchByTag(tag, ImageRequest.OPEN_SEARCH);
//                        searchByTag(tag);
                    }
                }.run();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setUpToolBar()
    {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("Tagged Wallpaper");
//        .setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu, menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_search:
//
//                //       onCall();   //your logic
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return true;
    }

    private void loadResources()
    {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
    }

    /**
     *  https://developer.android.com/training/search/setup.html
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
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
            // find reference to search fragment
            //SearchFragment frag = (SearchFragment) findFragmentByTitle(SearchFragment.SEARCH);
            //if(frag != null)
                // pass query into fragment
                //frag.searchByTag(query.trim());
        }
    }

    /**
     * Set up the different tabs that relate to the different fragments
     */
    private void setUpTabs()
    {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(FRAG_AMOUNT);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        TabLayout tab_layout = (TabLayout) findViewById(R.id.main_tab_layout);
        tab_layout.setupWithViewPager(mViewPager);
    }

    /**
     * Load the different fragments into the adapter.
     * This one will eventually be loaded into a viewpager and tabs
     */
    private void setUpFragmentAdapters()
    {
        frags = new ArrayList<FragmentAdapter.FragmentInfo>() {{
            add(new FragmentAdapter.FragmentInfo(CategoryFragment.newInstance(), CategoryFragment.CATEGORY_DATABASE_NAME));
            //add(new FragmentAdapter.FragmentInfo(PopularFragment.newInstance(), PopularFragment.POPULAR));
            //add(new FragmentAdapter.FragmentInfo(SearchFragment.newInstance(), SearchFragment.SEARCH));
            add(new FragmentAdapter.FragmentInfo(SimilarImagesFragment.newInstance(), SimilarImagesFragment.SIMILAR));
        }};

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), frags);

        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();

        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void searchFragmentByTitle(String title) {

        int pos = 0;

        for(FragmentAdapter.FragmentInfo fragInfo : this.frags)
        {
            if(fragInfo.getTitle().equals(title))
                mViewPager.setCurrentItem(pos);
            else
                pos++;
        }
    }

    @Override
    public Fragment findFragmentByTitle(String title) {

        int pos = 0;

        for(FragmentAdapter.FragmentInfo fragInfo : this.frags)
        {
            if(fragInfo.getTitle().equals(title))
                return fragInfo.getFrag();
        }

        return null;
    }
}