
package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.fragments.CategoryFragment;
import io.tanners.taggedwallpaper.fragments.SimilarImagesFragment;
import io.tanners.taggedwallpaper.adapters.FragmentAdapter;
import io.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;

public class MainActivity extends AppCompatActivity implements IFindFragment, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private List<FragmentAdapter.FragmentInfo> frags;
    private ViewPager mViewPager;
    private final int FRAG_AMOUNT = 3;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setHasOptionsMenu(true);

        setUpToolBar();
        setUpDrawer();
        setUpNav();
        loadResources();
        // set up fragment tabs
        setUpTabs();
        // set up fragments into adapter
        setUpFragmentAdapters();
        // handle search queries
        handleSearch(getIntent());

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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
        final SearchView mSearchView = (SearchView) mSearchBarMenuItem.getActionView();

//        mSearchBarMenuItem.setIcon(R.drawable.ic_action_search_white);
//        mSearchBarMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

//        mSearchBarMenuItem.setTitle("TITLE");

        ComponentName mComponentName = new ComponentName(this, MainActivity.class);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(mComponentName));
//        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, MainActivity.class)));

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);

//        mSearchView.setIconifiedByDefault(true);

        // https://stackoverflow.com/questions/18737464/how-to-make-the-action-bar-searchview-fill-entire-action-bar
        mSearchView.setIconifiedByDefault(false);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mSearchView.setLayoutParams(params);
//        mSearchBarMenuItem.expandActionView();

//        mSearchView.






       // mSearchView.setIconifiedByDefault(true);
        //mSearchView.setQueryHint("Search...");

//        final SearchView finalSearch_view = mSearchView;
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                final String tag = mSearchView.getQuery().toString();
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i("SEARCH", "DEBUG 222: " + tag);
//
////                        MenuItemCompat.collapseActionView(search_bar);
////                        search_bar.collapseActionView();
////                        mSearchView.clearFocus();
////                        mSearchView.setQuery("", false);
////                        mSearchView.setFocusable(false);
////                        searchByTag(tag, ImageRequest.OPEN_SEARCH);
//                        //searchByTag(tag);
//                    }
//                }.run();
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    private void setUpToolBar()
    {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);

       // setSupportActionBar(toolbar);
       // mToolbar.setTitle("");
//        .setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //setUpDrawer(mToolbar);
    }

    // https://stackoverflow.com/questions/26754940/appcompatv7-v21-navigation-drawer-not-showing-hamburger-icon
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mToggle.onConfigurationChanged(newConfig);
        mToggle.syncState();
    }

    private void setUpDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // This method was deprecated in API level 23.2.0. Use addDrawerListener(DrawerListener)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            drawer.addDrawerListener(mToggle);
        else
            drawer.setDrawerListener(mToggle);

        // just in case
        mToggle.syncState();

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setUpNav()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void loadResources()
    {
       // Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        //setSupportActionBar(myToolbar);
    }

    /**
     *  https://developer.android.com/training/search/setup.html
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i("SEARCH", "DEBUG !!!!!!!: " );
        setIntent(intent);
        // handle search queries
        handleSearch(intent);
    }

    /**
     * Used to handle search intents
     * @param intent
     */
    private void handleSearch(Intent intent) {
        Log.i("SEARCH", "DEBUG 000: " );
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {


            // get search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("SEARCH", "DEBUG 111: " + query);

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



            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }



            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            //super.onBackPressed();


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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onClose() {
        Log.i("SEARCH", "DEBUG 123: " );
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("SEARCH", "DEBUG 456: " );
        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("SEARCH", "DEBUG 789: " );
        return false;
    }
}