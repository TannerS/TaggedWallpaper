package io.tanners.taggedwallpaper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import io.tanners.taggedwallpaper.adapters.FragmentAdapter;
import io.tanners.taggedwallpaper.animations.ZoomOutPageTransformer;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;

public class TabbedActivity extends AppCompatActivity implements IFindFragment {
    protected List<FragmentAdapter.FragmentInfo> frags;
    protected ViewPager mViewPager;
    protected Toolbar mToolbar;

    /**
     * srt up action bar
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
        Log.d("LOADS", "DEBUG 3");
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
     * @param frags
     */
    protected void setUpFragmentAdapters(ArrayList<FragmentAdapter.FragmentInfo> frags)
    {
        this.frags = frags;
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), this.frags);
        mViewPager.setAdapter(adapter);
    }

    /**
     * find fragment by title and set viewpager to go there
     * @param title
     */
    @Override
    public void searchFragmentByTitle(String title) {

        int pos = 0;

        for(FragmentAdapter.FragmentInfo fragInfo : this.frags)
        {
            if(fragInfo.getTitle().equals(title))
                // set viewpager to go there
                mViewPager.setCurrentItem(pos);
            else
                pos++;
        }
    }

    /**
     * find fragment by title and return it's refernce
     * @param title
     * @return
     */
    @Override
    public Fragment findFragmentByTitle(String title) {

        int pos = 0;

        for(FragmentAdapter.FragmentInfo fragInfo : this.frags)
        {
            if(fragInfo.getTitle().equals(title))
                // return frag
                return fragInfo.getFrag();
        }

        return null;
    }

}
