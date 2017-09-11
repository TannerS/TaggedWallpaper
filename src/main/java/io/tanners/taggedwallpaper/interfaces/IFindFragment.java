package io.tanners.taggedwallpaper.interfaces;


import android.support.v4.app.Fragment;

public interface IFindFragment {
    public Fragment findFragmentByTitle(String title);
    public void searchFragmentByTitle(String title);
}