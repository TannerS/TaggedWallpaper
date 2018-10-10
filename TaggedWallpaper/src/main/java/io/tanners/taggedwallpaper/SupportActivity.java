package io.tanners.taggedwallpaper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import io.tanners.taggedwallpaper.adapters.fragment.FragmentAdapter;
import io.tanners.taggedwallpaper.interfaces.IFindFragment;
import io.tanners.taggedwallpaper.support.transformer.fragment.ZoomOutPageTransformer;

/**
 * Base class to hold tabbed fragments
 */
public class SupportActivity extends NetworkActivity {
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
}
