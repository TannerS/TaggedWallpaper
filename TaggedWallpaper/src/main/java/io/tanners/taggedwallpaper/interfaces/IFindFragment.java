package io.tanners.taggedwallpaper.interfaces;

import android.support.v4.app.Fragment;

/**
 * An interface used by the activity to locate a specific fragment to do some task for/on it
 */
public interface IFindFragment {
    public Fragment findFragmentByTitle(String title);
    public void searchFragmentByTitle(String title);
}
