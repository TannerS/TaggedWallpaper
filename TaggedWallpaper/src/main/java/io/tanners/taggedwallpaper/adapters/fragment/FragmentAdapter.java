package io.tanners.taggedwallpaper.adapters.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * Fragment adapter to hold fragments with built in functionality
 */
public class FragmentAdapter extends FragmentStatePagerAdapter
{
    private List<FragmentWrapper> frags;

    public FragmentAdapter(FragmentManager manager, List<FragmentWrapper> frags)
    {
        super(manager);
        this.frags = frags;
    }

    /**
     * Get count of items
     * @return
     */
    @Override
    public int getCount() {
        return this.frags != null ? frags.size() : 0;
    }

    /**
     * Get page row fagment based on position
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position)
    {
        return this.frags != null ? this.frags.get(position).getFrag() : null;
    }

    /**
     * Get page row tite based on position
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.frags != null ? frags.get(position).getTitle() : null;
    }

    /**
     * wrapper class to hold fragments and their tiles
     */
    public static class FragmentWrapper
    {
        private String title;
        private Fragment frag;

        public FragmentWrapper(Fragment frag, String title)
        {
            this.frag = frag;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public Fragment getFrag() {
            return frag;
        }
    }
}