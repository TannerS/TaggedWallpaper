
package io.tanners.taggedwallpaper;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.FragmentAdapter;
import io.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;

public class MainActivity extends AppCompatActivity implements IFindFragment{
    private List<FragmentAdapter.FragmentInfo> frags;
    private ViewPager mViewPager;
    private final int FRAG_AMOUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up fragment tabs
        setUpTabs();
        // set up fragments into adapter
        setUpFragmentAdapters();
        // handle search queries
        handleSearch(getIntent());

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


        Log.i("SEARCH", "DEBUG 6");



        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            Log.i("SEARCH", "DEBUG 7");


            // get search query

            String query = intent.getStringExtra(SearchManager.QUERY);
            // find reference to search fragment
            SearchFragment frag = (SearchFragment) findFragmentByTitle(SearchFragment.SEARCH);

            if(frag != null)
                // pass query into fragment
                frag.searchByTag(query.trim());
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
        Log.i("LOAD", "LOADING FRAGMENTS");
        frags = new ArrayList<FragmentAdapter.FragmentInfo>() {{
            add(new FragmentAdapter.FragmentInfo(CategoryFragment.newInstance(), CategoryFragment.CATEGORY_DATABASE_NAME));
            add(new FragmentAdapter.FragmentInfo(PopularFragment.newInstance(), PopularFragment.POPULAR));
            add(new FragmentAdapter.FragmentInfo(SearchFragment.newInstance(), SearchFragment.SEARCH));
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


////public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IFindFragment

