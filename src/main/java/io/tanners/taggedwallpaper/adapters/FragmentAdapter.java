package io.tanners.taggedwallpaper.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

// TODO https://stackoverflow.com/questions/18747975/difference-between-fragmentpageradapter-and-fragmentstatepageradapter
public class FragmentAdapter extends FragmentStatePagerAdapter
{
    private List<FragmentInfo> frags;

    public FragmentAdapter(FragmentManager manager, List<FragmentInfo> frags)
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
        return this.frags != null ? 0 : frags.size();
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
        return frags.get(position).getTitle();
    }

    /**
     * wrapper class to hold fragments and their tiles
     */
    public static class FragmentInfo
    {
        private String title;
        private Fragment frag;

        public FragmentInfo(Fragment frag, String title)
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