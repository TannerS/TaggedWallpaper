package com.tanners.taggedwallpaper.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter
{
//    List<Fragment> frags;
//    List<String> titles;
    List<FragmentInfo> frags;

    public FragmentAdapter(FragmentManager manager)
    {
        super(manager);



        this.frags = new ArrayList<FragmentInfo>();
//        frags = new ArrayList<Fragment>();
//        titles = new ArrayList<String>();
    }

    @Override
    public int getCount()
    {
        return frags.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        return frags.get(position).getFrag();
    }

    public void addTab(Fragment frag, String title)
    {
        frags.add(new FragmentInfo(title, frag));
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return frags.get(position).getTitle();
    }

    private class FragmentInfo
    {
        private String title;
        private Fragment frag;

        public FragmentInfo(String title, Fragment frag)
        {
            this.frag = frag;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Fragment getFrag() {
            return frag;
        }

        public void setFrag(Fragment frag) {
            this.frag = frag;
        }
    }
}