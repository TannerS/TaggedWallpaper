package io.tanners.taggedwallpaper.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

// TODO https://stackoverflow.com/questions/18747975/difference-between-fragmentpageradapter-and-fragmentstatepageradapter
//public class FragmentAdapter extends FragmentPagerAdapter
public class FragmentAdapter extends FragmentStatePagerAdapter
{
    private List<FragmentInfo> frags;

    public FragmentAdapter(FragmentManager manager, List<FragmentInfo> frags)
    {
        super(manager);
        this.frags = frags;
    }

    @Override
    public int getCount()
    {
        return this.frags.size() > 0 ? frags.size() : 0;
    }

    @Override
    public Fragment getItem(int position)
    {
//        manager.beginTransaction().

//        switch (position) {
//            case 0:
//                return ListProductsFragment.newInstance();
//            case 1:
//                return ListActiveSubstancesFragment.newInstance();
//            case 2:
//                return ListProductFunctionsFragment.newInstance();
//            case 3:
//                return ListCropsFragment.newInstance();
//            default:
//                return null;
//        }

        return this.frags != null ? this.frags.get(position).getFrag() : null;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return frags.get(position).getTitle();
    }

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

//        public void setTitle(String title) {
//            this.title = title;
//        }

        public Fragment getFrag() {
            return frag;
        }
    }
}