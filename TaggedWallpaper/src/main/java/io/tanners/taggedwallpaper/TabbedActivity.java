package io.tanners.taggedwallpaper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import io.tanners.taggedwallpaper.adapters.fragment.FragmentAdapter;
import io.tanners.taggedwallpaper.support.transformer.fragment.ZoomOutPageTransformer;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;

/**
 * Base class to hold tabbed fragments
 */
public class TabbedActivity extends AppCompatActivity implements IFindFragment {
    protected List<FragmentAdapter.FragmentWrapper> frags;
    protected ViewPager mViewPager;
    protected Toolbar mToolbar;

    /**
     * set up action bar
     *
     * @param id
     */
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
        mViewPager = (ViewPager) findViewById(mPageViewerId);
        // limit of screens before memory destroys them
        // needed to prevent view destroy and reloads images each time
        mViewPager.setOffscreenPageLimit(mFragAmount);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        // load tabs
        TabLayout tab_layout = (TabLayout) findViewById(mTabLayoutId);
        // bind tabs with viewpager
        tab_layout.setupWithViewPager(mViewPager);
    }

    /**
     * load fragments
     *
     * @param frags
     */
    protected void setUpFragmentAdapters(ArrayList<FragmentAdapter.FragmentWrapper> frags)
    {
        this.frags = frags;
        // set fragments into adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), this.frags);
        // set adapter into pageviewer
        mViewPager.setAdapter(adapter);
    }

    /**
     * find fragment by title and set viewpager to go there
     *
     * @param title
     */
    @Override
    public void searchFragmentByTitle(String title) {

        int pos = 0;

        for(FragmentAdapter.FragmentWrapper fragInfo : this.frags) {
            if (fragInfo.getTitle().equals(title)) {
                // set viewpager to go there
                mViewPager.setCurrentItem(pos);
                return;
            }
            // set for next pos
            pos++;
        }
    }

    /**
     * find fragment by title and return it's reference
     *
     * @param title
     * @return
     */
    @Override
    public Fragment findFragmentByTitle(String title) {
        // loop fragments to find the correct one
        for(FragmentAdapter.FragmentWrapper fragInfo : this.frags)
        {
            if(fragInfo.getTitle().equals(title))
                // return frag
                return fragInfo.getFrag();
        }

        return null;
    }
}
