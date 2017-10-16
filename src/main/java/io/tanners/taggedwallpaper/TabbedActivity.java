package io.tanners.taggedwallpaper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.FragmentAdapter;
import io.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import io.tanners.taggedwallpaper.fragments.CategoryFragment;
//import io.tanners.taggedwallpaper.fragments.SimilarImagesFragment;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;

public class TabbedActivity extends AppCompatActivity implements IFindFragment {
    protected List<FragmentAdapter.FragmentInfo> frags;
    protected ViewPager mViewPager;
//    protected int mFragAmount;
    protected Toolbar mToolbar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_tabbed);
//    }

    protected void setUpToolBar(int id)
    {
        mToolbar = (Toolbar) findViewById(id);
        setSupportActionBar(mToolbar);
    }

    /**
     * Set up the different tabs that relate to the different fragments
     */
    protected void setUpTabs(int mPageViewerId, int mTabLayoutId, int mFragAmount)
    {
        Log.d("LOADS", "DEBUG 3");
        mViewPager = (ViewPager) findViewById(mPageViewerId);
        mViewPager.setOffscreenPageLimit(mFragAmount);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        TabLayout tab_layout = (TabLayout) findViewById(mTabLayoutId);
        tab_layout.setupWithViewPager(mViewPager);
    }

    protected void setUpFragmentAdapters(ArrayList<FragmentAdapter.FragmentInfo> frags)
    {
        this.frags = frags;

        Log.d("LOADS", "DEBUG 4: " + this.frags.size());
//        frags = new ArrayList<FragmentAdapter.FragmentInfo>() {{
//            add(new FragmentAdapter.FragmentInfo(CategoryFragment.newInstance(), CategoryFragment.CATEGORY));
//            //add(new FragmentAdapter.FragmentInfo(PopularImagesFragment.newInstance(), PopularImagesFragment.POPULAR));
//            //add(new FragmentAdapter.FragmentInfo(SearchFragment.newInstance(), SearchFragment.SEARCH));
//            add(new FragmentAdapter.FragmentInfo(SimilarImagesFragment.newInstance(), SimilarImagesFragment.SIMILAR));
//        }};

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), this.frags);

        Log.d("LOAD", "DEBUG 2: " + frags.size());
        mViewPager.setAdapter(adapter);
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
